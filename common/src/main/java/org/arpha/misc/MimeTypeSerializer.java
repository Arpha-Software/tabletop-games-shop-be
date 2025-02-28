package org.arpha.misc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.util.MimeType;

import java.io.IOException;

public class MimeTypeSerializer extends JsonSerializer<MimeType> {

    @Override
    public void serialize(MimeType mimeType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (mimeType != null) {
            jsonGenerator.writeString(mimeType.toString());
        }
    }

}
