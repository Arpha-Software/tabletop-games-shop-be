package org.arpha.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.MimeType;

@Converter(autoApply = true)
public class MimeConverter implements AttributeConverter<MimeType, String> {

    @Override
    public String convertToDatabaseColumn(MimeType attribute) {
        return attribute.toString();
    }

    @Override
    public MimeType convertToEntityAttribute(String dbData) {
        return MimeType.valueOf(dbData);
    }
}
