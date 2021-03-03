package com.example.apachekafka.serializer;

import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

public class ObjectDeserializer implements Deserializer<Object> {

	@Override
	public void close() {
		
	}

	@Override
	public void configure(Map<String, ?> m, boolean b) {
		
	}

	@Override
	public Object deserialize(String s, byte[] ba) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(ba);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			return null;
		}
	}

}
