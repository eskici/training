package com.avivasa.maf.service.faxrobot.enums;

public enum DocumentStatusEnum {
/*
	1	Uygulamadan Dokuman Eklendi
	2	Tabloya Kayıt Atıldı
	3	CM  e kaydedildi
	4	Indeks Istasyonuna Atıldı
	5	Akışa yönlendirildi
	9	Dosya Bölündü
	11	Bilgi Amaçlı Bölünen Dosyalar
	12	Bilgi Amaçlı Dosya Bölündü
	14	Bilgi Amaçlı Indeks Istasyonuna Atıldı
	15	Bilgi Amaçlı Akışa Yönlendirildi
	-1	Hatalı Döküman
	21	Uygulamadan Dokuman Eklendi
	24	Indeks Istasyonuna Atıldı
	25	Akışa yönlendirildi
	*/
	
	DURUM_FILE_CREATED(2),

	DURUM_FILE_CREATED_FROM_APPLICATION(1),

	DURUM_HATALI_DOKUMAN(-1),

	DURUM_INSERTED_TO_TABLE(2),

	DURUM_INVOLVED_CM(3),

	DURUM_LOGGED_PROCESSED(14),

	DURUM_PROCESSED(4),

	DURUM_SPLITTED(9), 

	DURUM_SPLITTED_DOCS_LOGGED(11),

	DURUM_SPLITTED_FOR_LOG(12),

	DURUM_WEBSERVICE_LOGGED_PROCESSED(15),

	DURUM_WEBSERVICE_PROCESSED(5);

	private Integer statusCode;

	DocumentStatusEnum(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getStatusCode() {
		return statusCode;
	}
}
