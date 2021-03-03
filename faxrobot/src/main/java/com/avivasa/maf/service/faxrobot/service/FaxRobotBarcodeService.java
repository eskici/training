package com.avivasa.maf.service.faxrobot.service;

import com.aspose.barcode.License;
import com.aspose.barcode.barcoderecognition.BarCodeReader;
import com.aspose.barcode.barcoderecognition.DecodeType;
import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.FaxRobotMimeTypeEnum;
import com.avivasa.maf.service.faxrobot.splitters.FileSplitter;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FaxRobotBarcodeService extends FaxRobotDirectories  {
	private Logger log = LoggerFactory.getLogger(FaxRobotBarcodeService.class);

    @Autowired
    private Map<String, FileSplitter> fileSplitterMap;

    @Autowired
	private Environment env;

	@PostConstruct
	public void initialize() {
		setBarcodeLicence();
	}

    public DocumentDto readBarcodesOnDocument(DocumentDto parentDocumentDto) throws Exception {
		setMimeType(parentDocumentDto);
        createImagesBeforeReadingBarcode(parentDocumentDto);
        Map<Integer, String> barcodeMap = getBarcodesByMap(getDirConvertToImage(), parentDocumentDto);
        parentDocumentDto.setBarcodeMap(barcodeMap);
        return parentDocumentDto;
    }

	public void deleteImageDirectoryByDocumentNo(Integer documentNo) {
		String directoryPath = getDirConvertToImage() + documentNo;

		try {
			FileUtils.deleteDirectory(new File(directoryPath));
			log.info("{} dizini silindi", directoryPath);
		} catch (IOException ioex) {
			log.info("{} dizini silinirken hata oluştu", directoryPath, ioex);
		}
	}

    private void createImagesBeforeReadingBarcode(DocumentDto document) throws Exception {
		createImageDirectoryByDocumentNo(document.getDocumentNo());

		for (FileSplitter fileSplitter : fileSplitterMap.values()) {

			if(fileSplitter.matches(document.getMimeType())){
				fileSplitter.splitFileIntoPages(document);
			}
		}
	}

    private void createImageDirectoryByDocumentNo(Integer documentNo) {
        String directoryPath = getDirConvertToImage() + documentNo;

        if(!new File(directoryPath).exists()) {
            new File(directoryPath).mkdir();
            log.info("{} dizini oluşturuldu", directoryPath);
        }
    }

	private void setMimeType(DocumentDto documentDto) throws Exception {

		File file = new File(documentDto.getNewDocumentPath());
		try {
			String mimeType = Files.probeContentType(file.toPath());
			documentDto.setMimeType(FaxRobotMimeTypeEnum.getMimeTypeEnum(mimeType));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Map<Integer, String> getBarcodesByMap(String dirConvertToImage, DocumentDto parentDocumentDto){

		log.info("{} için getBarcodesByMap süreci başlatıldı.", parentDocumentDto.getInfoLog());
		Map<Integer, String> barcodeMap = new HashMap<>();

		File imageDir = new File(dirConvertToImage + parentDocumentDto.getDocumentNo());

		Collection<File> imagesList = FileUtils.listFiles(imageDir, null, false);
		
		int pageIndex = 1;
		for (File image : imagesList) {
			String barcodeTxt = getBarcodeText(image.getAbsolutePath());
			
			if (FaxRobotUtil.isValidBarcode(barcodeTxt)) {
				log.info("{} dosyasının {}. sayfasında {} nolu barkod okundu.", parentDocumentDto.getInfoLog(), pageIndex, barcodeTxt);
				barcodeMap.put(pageIndex, barcodeTxt);
			}
			pageIndex++;
		}
		return barcodeMap;
	}


	private void setBarcodeLicence() {
		
		try {
			String licenseFile = env.getProperty("faxrobot.licence.file");

			if (!StringUtils.isEmpty(licenseFile)) {
				License license = new License();
				license.setLicense(String.valueOf(new File(licenseFile)));
			} else {
				String encryptedFile = env.getProperty("faxrobot.licence.encryptedFile");
				String keyFile = env.getProperty("faxrobot.licence.keyFile");

				byte[] decryptedLicense = FaxRobotUtil.decryptLicense(encryptedFile, keyFile);

				// Load the decrypted license into a stream and set the license.
				ByteArrayInputStream licenseStream = new ByteArrayInputStream(decryptedLicense);

				License license = new License();
				license.setLicense(licenseStream);
			}

			log.info("Aspose barcode licence is registered");
		} catch (Exception e) {
			log.error("setBarcodeLicence exception :", e);
		}
	}
	
	private String getBarcodeText(String filePath) {
		BarCodeReader reader = new BarCodeReader(filePath, DecodeType.INTERLEAVED_2_OF_5);
		while (reader.read()) {
			return reader.getCodeText();
		}
		
		return null;
	}
}