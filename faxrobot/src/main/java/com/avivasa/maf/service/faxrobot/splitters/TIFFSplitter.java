package com.avivasa.maf.service.faxrobot.splitters;

import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.service.FaxRobotDirectories;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.FaxRobotMimeTypeEnum;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Taner YILDIRIM
 */
@Service
public class TIFFSplitter extends FaxRobotDirectories implements FileSplitter {

    private Logger log = LoggerFactory.getLogger(TIFFSplitter.class);

    private ImageReader tiffImageReader;

    @PostConstruct
    public void initialize() {
        setTiffImageReader();
    }

    @Override
    public DocumentDto splitFileIntoPages(DocumentDto parentDocumentDto) {
        int pageIndex = 1;

        try {
            List<BufferedImage> images = extractImages(new FileInputStream(parentDocumentDto.getNewDocumentPath()));
            for (BufferedImage bufferedImage : images) {
                createSplittedImage(parentDocumentDto, pageIndex, bufferedImage);
                pageIndex++;
            }
        } catch (Exception e) {
            log.error("splitTifImage has an error ", e);
        }
        parentDocumentDto.setPageNumber(pageIndex);
        return parentDocumentDto;
    }

    @Override
    public boolean matches(FaxRobotMimeTypeEnum mimeType) {
        return FaxRobotUtil.isTIF(mimeType);
    }

    private List<BufferedImage> extractImages(InputStream fileInput) throws IOException {
        List<BufferedImage> extractedImages = new ArrayList<>();

        try (ImageInputStream iis = ImageIO.createImageInputStream(fileInput)) {

            tiffImageReader.setInput(iis);

            int pages = tiffImageReader.getNumImages(true);
            for (int imageIndex = 0; imageIndex < pages; imageIndex++) {
                BufferedImage bufferedImage = tiffImageReader.read(imageIndex);
                extractedImages.add(bufferedImage);
            }
        }

        return extractedImages;
    }

    /**
     * @param parentDocumentDto
     * @param pageIndex
     * @param bufferedImage
     * @throws Exception
     */
    private File createSplittedImage(DocumentDto parentDocumentDto, int pageIndex, BufferedImage bufferedImage) {
        File splittedImageFile = null;

        try {
            String imageFileName = FaxRobotUtil.getFilename(parentDocumentDto.getDocumentName()) + "_" + pageIndex + ".tif";
            String imageDirectory = getDirConvertToImage() + parentDocumentDto.getDocumentNo();

            splittedImageFile = new File(imageDirectory + File.separator + imageFileName);
            ImageIO.write(bufferedImage, "TIF", splittedImageFile);
            log.info("Barkod okuma öncesi {} dosyasının {}. sayfası {} resim formatına dönüştürüldü.", parentDocumentDto.getNewDocumentPath(), pageIndex, splittedImageFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("splitImageDocument has an error ", e);
        }
        return splittedImageFile;
    }

    private void setTiffImageReader() {
        Iterator<ImageReader> imageReaders = ImageIO.getImageReadersByFormatName("tiff");
        if (!imageReaders.hasNext()) {
            throw new UnsupportedOperationException("No TIFF Reader found!");
        }
        tiffImageReader = imageReaders.next();
    }
}
