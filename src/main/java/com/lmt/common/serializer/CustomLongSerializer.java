package com.lmt.common.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author ducx
 * @date 2017-07-29
 * long型序列化成字符串型，防止前端js处理时失精
 *
 */
public class CustomLongSerializer extends JsonSerializer<Long> {

	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		gen.writeString(String.valueOf(value));
	}

	@Override
	public Class<Long> handledType() {
		return Long.class;
	}
}
