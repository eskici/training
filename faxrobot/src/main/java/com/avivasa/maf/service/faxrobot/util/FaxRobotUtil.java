package com.avivasa.maf.service.faxrobot.util;

import com.avivasa.exception.MafSystemException;
import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.FaxRobotMimeTypeEnum;
import com.avivasa.maf.util.MafUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FaxRobotUtil {

	private static Logger log = LoggerFactory.getLogger(FaxRobotUtil.class);

	private FaxRobotUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean isValidDocument(File file) {
		if (file.length() < 2000) {
			return false;
		}

		return isSupportedFile(file);
	}

	public static void copyFile(File src, File dst) throws IOException {
		FileUtils.copyFile(src, dst);
		log.info("{} dosyası {} adresine kopyalandı", src.getAbsolutePath(), dst.getAbsolutePath());
	}

	public static String replaceTurkishCharacters(String fileName) {

		fileName = fileName.replaceAll("ü", "u");
		fileName = fileName.replaceAll("ç", "c");
		fileName = fileName.replaceAll("ğ", "g");
		fileName = fileName.replaceAll("ş", "s");
		fileName = fileName.replaceAll("ı", "i");
		fileName = fileName.replaceAll("ö", "o");
		fileName = fileName.replaceAll(" ", "");
		return fileName;
	}

	public static String getFilename(String filenameWithExtension) throws IOException {
		if(MafUtils.isEmpty(filenameWithExtension))
			throw new IOException("empty file name");

		int indexOfDot = filenameWithExtension.lastIndexOf('.');

		if(indexOfDot != -1) {
			return filenameWithExtension.substring(0, indexOfDot);
		} else {
			return filenameWithExtension;
		}
	}

	public static String getFileExtension(String filename) throws MafSystemException {
		if(MafUtils.isEmpty(filename))
			throw new MafSystemException("empty file name");

		int indexOfDot = filename.lastIndexOf('.');

		if(indexOfDot != -1) {
			return filename.substring(indexOfDot+1, filename.length());
		} else {
			throw new MafSystemException("no extension found on file");
		}
	}

	// 9239300343_1.pdf, 9239300343_2.pdf
	public static String getSplittedFilename(String fileName, int pageIndex, String extension) {
		StringBuilder buff = new StringBuilder();
		buff.append(fileName);
		buff.append("_");
		buff.append(pageIndex);
		buff.append(".");
		buff.append(extension);
		return buff.toString();
	}

	public static boolean isTransferredPolicy(DocumentDto parentDocumentDto) {
		Map<Integer, String> barcodeMap = parentDocumentDto.getBarcodeMap();

		for (String barcodeTxt : barcodeMap.values()) {
			if (barcodeTxt != null && barcodeTxt.length() == 10 && MafUtils.isNumber(barcodeTxt)) {
				final String formTip = barcodeTxt.substring(0, 2);
				if ("02".equalsIgnoreCase(formTip) || "03".equalsIgnoreCase(formTip)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean wantsToBeSplitted(String documentName) {
		if (documentName != null) {
			final String doc = documentName;
			final int place_of_point = doc.lastIndexOf('.');
			if (place_of_point != -1 && doc.substring(0, place_of_point).length() >= 2) {
				final String sTemp = doc.substring(place_of_point - 2, place_of_point);
				if ("_p".equalsIgnoreCase(sTemp)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void createSplittedFile(String destination, int pageIndex,
										  PdfReader reader) throws IOException, DocumentException {

		// create destination file name
		log.info("Writing " + destination);

		// create new document with corresponding page size
		Document document = new Document(reader.getPageSizeWithRotation(1));

		// create writer and assign document and destination
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(destination));
		document.open();

		// read original page and copy to writer
		PdfImportedPage page = copy.getImportedPage(reader, pageIndex);
		copy.addPage(page);

		// close and write the document
		document.close();
	}

	public static String createNewFile(String fileName) {

		final SimpleDateFormat sdformatFileName = new SimpleDateFormat("yyyyMMddHHmmssSS");
		final String fileNamePrefix = sdformatFileName.format(new Date());

		String newFileName = "";
		int ixOfPoint = fileName.lastIndexOf('.');
		if (ixOfPoint != -1) {
			String lastPart = fileName.substring(ixOfPoint, fileName.length());
			newFileName = fileNamePrefix + lastPart;
		}
		return newFileName;
	}

	/**
	 * @param directory
	 * @param documentNo
	 * @param extension
	 * @return
	 */
	public static String createFilePath(String directory, int documentNo, String extension) {

		final SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
		final java.util.Date date = new java.util.Date();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(directory);
		stringBuilder.append(File.separator);
		stringBuilder.append(documentNo);
		stringBuilder.append(File.separator);
		stringBuilder.append(documentNo);
		stringBuilder.append("_");
		stringBuilder.append(formatter.format(date));
		stringBuilder.append(".");
		stringBuilder.append(extension);
		return stringBuilder.toString();
	}

	public static boolean isValidBarcode(String barcodeTxt) {
		return !MafUtils.isEmpty(barcodeTxt) && barcodeTxt.length() == 10 && MafUtils.isNumeric(barcodeTxt);
	}

	public static boolean hasBarcode(DocumentDto parentDocumentDto) {
		return null != parentDocumentDto.getBarcodeMap() && !parentDocumentDto.getBarcodeMap().isEmpty();
	}

	public static String checkFileNameLength(String fileName) {
		// dosya ismi uzunsa duzeltiyor, kırpıyor, 80 karakter max
		final int ixOfPoint = fileName.lastIndexOf('.');
		if (ixOfPoint != -1) {
			final String lastPart = fileName.substring(ixOfPoint, fileName.length());
			String firstPart = fileName.substring(0, ixOfPoint);
			if (firstPart.length() > FaxRobotConstants.MAX_FILE_LENGTH) {
				firstPart = fileName.substring(0,  FaxRobotConstants.MAX_FILE_LENGTH);
				fileName = firstPart + lastPart;
			}
		}

		return fileName;
	}

	public static DocumentDto cloneDocumentDto(DocumentDto parentDocument) {
		DocumentDto childDocDto = new DocumentDto();
		childDocDto.setParentDocumentNo(parentDocument.getDocumentNo());
		childDocDto.setDocumentUser(parentDocument.getDocumentUser());
		childDocDto.setDocumentChannel(parentDocument.getDocumentChannel());
		childDocDto.setProcessType(parentDocument.getProcessType());
		childDocDto.setEntityType(parentDocument.getEntityType());
		return childDocDto;
	}

	public static boolean isTIF(FaxRobotMimeTypeEnum mimeType) {
		return FaxRobotMimeTypeEnum.IMAGE_TIFF.equals(mimeType);
	}

	public static boolean isSupportedImage(FaxRobotMimeTypeEnum mimeType) {
		return isJPG(mimeType) || isPNG(mimeType) || isBMP(mimeType);
	}

	public static boolean isPDF(FaxRobotMimeTypeEnum mimeType) {
		return FaxRobotMimeTypeEnum.APPLICATION_PDF.equals(mimeType);
	}

	public static boolean isPNG(FaxRobotMimeTypeEnum mimeType) {
		return FaxRobotMimeTypeEnum.IMAGE_PNG.equals(mimeType);
	}

	public static boolean isBMP(FaxRobotMimeTypeEnum mimeType) {
		return FaxRobotMimeTypeEnum.IMAGE_BMP.equals(mimeType) || FaxRobotMimeTypeEnum.IMAGE_X_WINDOWS_BMP.equals(mimeType);
	}

	public static boolean isJPG(FaxRobotMimeTypeEnum mimeType) {
		return FaxRobotMimeTypeEnum.IMAGE_JPEG.equals(mimeType) || FaxRobotMimeTypeEnum.IMAGE_PJPEG.equals(mimeType);
	}

	public static byte[] decryptLicense(String encryptedFile, String keyFile) throws IOException {
		byte[] licBytes = readAllBytesFromFile(encryptedFile);
		byte[] keyBytes = readAllBytesFromFile(keyFile);

		byte[] output = new byte[licBytes.length];

		for (int i = 0; i < licBytes.length; i++)
			output[i] = (byte)(licBytes[i] ^ keyBytes[i]);

		return output;
	}

	public static byte[] readAllBytesFromFile(String filePath) throws IOException
	{
		try (InputStream inputStream = new FileInputStream(filePath)) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int pos;
			while ((pos = inputStream.read()) != -1)
				bos.write(pos);
			return bos.toByteArray();
		}
	}

	private static boolean isSupportedFile(File file){

		try {
			String mimeType = Files.probeContentType(file.toPath());

			for (String supportedExtension : FaxRobotConstants.SUPPORTED_MIME_TYPES) {
				if (mimeType.equalsIgnoreCase(supportedExtension)) {
					return true;
				}
			}
		} catch (IOException ioex) {
			log.error("isSupportedFile method has error : ", ioex);
		}
		return false;
	}

	public static Timestamp todayTimestamp() {
		Date d1 = new Date();
		Timestamp time = new Timestamp(d1.getTime());

		return time;
	}

	public static long getMaxRecordSize(String maxRecordSize) {
		if(!MafUtils.isEmpty(maxRecordSize)) {
			return Long.valueOf(maxRecordSize);
		}
		return 25;
	}
}