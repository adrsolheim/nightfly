package no.adrsolheim.nightfly.util;

import org.springframework.security.crypto.codec.Hex;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Converter
public class TokenConverter implements AttributeConverter<String, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(String s) {
        return Hex.decode(s.replace("-",""));
    }

    @Override
    public String convertToEntityAttribute(byte[] b) {
        StringBuilder token = new StringBuilder(new String(b, StandardCharsets.UTF_8));
        token.insert(8,'-');
        token.insert(13,'-');
        token.insert(18,'-');
        token.insert(23,'-');
        return token.toString();
    }
}
