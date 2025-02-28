package org.arpha.misc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.MimeType;

import java.io.IOException;

public class MimeTypeDeserializer extends JsonDeserializer<MimeType> {

    @Override
    public MimeType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String mimeTypeString = jsonParser.getText();

        try {
            return MimeType.valueOf(mimeTypeString);
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Invalid MIME type: " + mimeTypeString);
        }
    }

}