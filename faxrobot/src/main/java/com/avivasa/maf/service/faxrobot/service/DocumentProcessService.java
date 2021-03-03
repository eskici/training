package com.avivasa.maf.service.faxrobot.service;

import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.DocumentStatusEnum;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Taner YILDIRIM
 */
@Service
public abstract class DocumentProcessService extends FaxRobotDirectories {

    private Logger log = LoggerFactory.getLogger(DocumentProcessService.class);

    @Autowired
    protected FaxRobotDYSService dysOperations;

    @Autowired
    protected FaxRobotDBService dbOperations;

    public abstract void processDocument(DocumentDto documentDto);

    public abstract boolean hasBarcode(DocumentDto documentDto);

    protected String addDocumentToFilenetAndUpdateDb(DocumentDto documentDto) throws Exception {
        String fileNetId = dysOperations.insertDocumentToFilenet(documentDto);
        documentDto.setCmRef(fileNetId);
        documentDto.setStatus(DocumentStatusEnum.DURUM_INVOLVED_CM.getStatusCode());
        dbOperations.updateDocument(documentDto);
        return fileNetId;
    }

    /**
     * @param parentDocumentDto
     * @return
     */
    protected List<DocumentDto> splitIntoChildDocuments(DocumentDto parentDocumentDto) {
        List<DocumentDto> childDocuments = new ArrayList<>();

        if(FaxRobotUtil.isPDF(parentDocumentDto.getMimeType()))  {
            childDocuments = createChildDocumentsForPDF(parentDocumentDto);
        } else if(FaxRobotUtil.isTIF(parentDocumentDto.getMimeType())) {
            childDocuments = createChildDocumentsForTIFF(parentDocumentDto);
        }
        return childDocuments;
    }

    private List<DocumentDto> createChildDocumentsForTIFF(DocumentDto parentDocumentDto) {

        log.info("{} için createChildDocumentsForTIFF süreci başlatıldı", parentDocumentDto.getInfoLog());
        List<DocumentDto> splittedDocuments = new ArrayList<>();
        File directory = new File(getDirConvertToImage() + parentDocumentDto.getDocumentNo());

        Collection<File> listOfFiles = FileUtils.listFiles(directory, null, false);

        int pageIndex = 1;
        for (File srcFile : listOfFiles) {

            try {
                File destFile = new File(getDirSplitToPages() + File.separator + srcFile.getName());
                FaxRobotUtil.copyFile(srcFile, destFile);

                DocumentDto childDoc = FaxRobotUtil.cloneDocumentDto(parentDocumentDto);
                childDoc.setDocumentName(parentDocumentDto.getDocumentName());
                childDoc.setNewDocumentName(destFile.getName());
                childDoc.setNewDocumentPath(destFile.getAbsolutePath());
                childDoc.setChildDocument(true);

                String barcodeTxt = parentDocumentDto.getBarcodeMap().get(pageIndex);
                if (FaxRobotUtil.isValidBarcode(barcodeTxt)) {
                    childDoc.setBarcodeId(barcodeTxt);
                }

                splittedDocuments.add(childDoc);
                pageIndex++;

                log.info("{} için {} child dosyası olusturuldu.", parentDocumentDto.getInfoLog(), childDoc.getDocumentName());
            } catch (Exception e) {
                log.error("createChildDocumentsForTIFF sürecinde hata oluştu ", e);
            }
        }

        return splittedDocuments;
    }

    private List<DocumentDto> createChildDocumentsForPDF(DocumentDto parentDocumentDto) {

        log.info("{} için createChildDocumentsForPDF süreci başlatıldı", parentDocumentDto.getInfoLog());
        List<DocumentDto> documentDtoList = new ArrayList<>();

        try {

            final FileInputStream fis = new FileInputStream(parentDocumentDto.getNewDocumentPath());
            final PdfReader inputPDF = new PdfReader(fis);
            String filenameWoExtension = FaxRobotUtil.getFilename(parentDocumentDto.getDocumentName());

            for (int pageIndex = 1; pageIndex <= inputPDF.getNumberOfPages(); pageIndex ++) {
                String newFileName = FaxRobotUtil.getSplittedFilename(filenameWoExtension, pageIndex, "pdf");

                FaxRobotUtil.createSplittedFile(getDirSplitToPages()+ newFileName, pageIndex, inputPDF);
                DocumentDto childDocDto = FaxRobotUtil.cloneDocumentDto(parentDocumentDto);
                childDocDto.setDocumentName(newFileName);
                childDocDto.setNewDocumentName(newFileName);
                childDocDto.setNewDocumentPath(getDirSplitToPages() + newFileName);
                childDocDto.setChildDocument(true);

                String barcodeTxt = parentDocumentDto.getBarcodeMap().get(pageIndex);
                if (FaxRobotUtil.isValidBarcode(barcodeTxt)) {
                    childDocDto.setBarcodeId(barcodeTxt);
                }
                documentDtoList.add(childDocDto);
                log.info("{} için {} child dosyası olusturuldu.", parentDocumentDto.getInfoLog(), childDocDto.getDocumentName());
            }
        } catch (final Exception exp) {
            log.error("{} için createChildDocumentsForPDF surecinde hata oluştu.", parentDocumentDto.getInfoLog(), exp);
        }

        return documentDtoList;
    }
}