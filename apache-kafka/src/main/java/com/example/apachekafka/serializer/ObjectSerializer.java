package com.example.apachekafka.serializer;

import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class ObjectSerializer implements Serializer<Object> {

	@Override
	public void close() {
				
	}

	@Override
	public void configure(Map<String, ?> m, boolean b) {
				
	}

	@Override
	public byte[] serialize(String s, Object o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			return new byte[0];
		}
	}

}
