package com.avivasa.maf.service.faxrobot.splitters;

import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.service.FaxRobotDirectories;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.FaxRobotMimeTypeEnum;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Taner YILDIRIM
 */
@Service
public class PDFSplitter extends FaxRobotDirectories implements FileSplitter {

    private Logger log = LoggerFactory.getLogger(PDFSplitter.class);

    @Autowired
    private Environment env;

    @Override
    public DocumentDto splitFileIntoPages(DocumentDto parentDocumentDto) {

        String dpi = env.getProperty("faxrobot.convert.dpi");
        log.info("{} nolu doküman için splitPdfAndConvertToImage süreci {} dpi değeri ile başlatıldı.", parentDocumentDto.getDocumentNo(), dpi);

        try {
            PDDocument pdfDocument = PDDocument.load(new File(parentDocumentDto.getNewDocumentPath()));
            int pageIndex = 1;

            PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);

            for (int page = 0; page < pdfDocument.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, Float.parseFloat(dpi), ImageType.GRAY);

                String imageFileName = FaxRobotUtil.getFilename(parentDocumentDto.getDocumentName()) + "_" + pageIndex + ".png";
                ImageIOUtil.writeImage(bim, getDirConvertToImage() + parentDocumentDto.getDocumentNo() + File.separator + imageFileName, Integer.parseInt(dpi));
                log.info("{} nolu doküman için barkod okuma öncesi {} dosyasının {}. sayfası {} resim dosyasına dönüştürüldü.", parentDocumentDto.getDocumentNo(), parentDocumentDto.getNewDocumentPath(), pageIndex, imageFileName);
                pageIndex++;
            }
            pdfDocument.close();
            parentDocumentDto.setPageNumber(pageIndex);
        } catch (IOException ioex) {
            log.error(": {}", ioex);
        }
        return parentDocumentDto;
    }

    @Override
    public boolean matches(FaxRobotMimeTypeEnum mimeType) {return FaxRobotUtil.isPDF(mimeType);}
}
