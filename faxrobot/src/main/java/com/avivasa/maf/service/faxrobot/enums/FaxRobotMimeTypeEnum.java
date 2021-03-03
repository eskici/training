package com.avivasa.maf.service.faxrobot.enums;

import org.springframework.util.StringUtils;

/**
 * @author Taner YILDIRIM
 */
public enum FaxRobotMimeTypeEnum {

    APPLICATION_PDF("application/pdf",".pdf"),

    IMAGE_TIFF("image/tiff",".tiff"),

    IMAGE_JPEG("image/jpeg",".jpg"),

    IMAGE_PJPEG("image/pjpeg",".jpeg"),

    IMAGE_BMP("image/bmp",".bmp"),

    IMAGE_X_WINDOWS_BMP("image/x-windows-bmp",".bmp"),

    IMAGE_PNG("image/png",".png");

    private final String mimeType;
    private final String extension;

    FaxRobotMimeTypeEnum(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public static FaxRobotMimeTypeEnum getMimeTypeEnum(String mimeType) throws Exception {

        if(!StringUtils.isEmpty(mimeType)) {

            for (FaxRobotMimeTypeEnum faxRobotMimeType : FaxRobotMimeTypeEnum.values()) {
                if (faxRobotMimeType.getMimeType().equalsIgnoreCase(mimeType)) {
                    return faxRobotMimeType;
                }
            }
        }

        throw new Exception("Unsupported mime type : " + mimeType);
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getExtension() {
        return extension;
    }
}