package com.avivasa.maf.service.faxrobot.util;

public class FaxRobotConstants {
	
	private FaxRobotConstants() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final int MAX_FILE_LENGTH = 80;
	public static final String[] SUPPORTED_MIME_TYPES = {"application/pdf", "image/tiff", "image/jpeg", "image/pjpeg", "image/bmp", "image/x-windows-bmp", "image/png"};
	public static final String INDEXLENMEMIS = "INDEXLENMEMIS";
	public static final String NOT_COMPLETED = "HAYIR";
	public static final String COMPLETED = "EVET";
	public static final String FAXROBOT_USER = "faxrobot";
	public static final String DEFAULT_REGISTRY_NO = "110088";
	public static final String LICENCE_FILE_PATH = "/appdocs/servis/dispatch/faxrobot/Aspose.Barcode.Enc.lic";
	public static final String LICENCE_KEY_PATH = "/appdocs/servis/dispatch/faxrobot/Aspose.Barcode.Enc.lic";
	public static final String EVRAK = "EVRAK";
	public static final String TRANSFER_PATH = "AktarimIslemleri";
	public static final String FAX_PATH = "MusteriHizmetleri";
}
