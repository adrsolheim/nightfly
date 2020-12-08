package no.adrsolheim.nightfly.util;

import org.springframework.security.crypto.codec.Hex;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Converter
public class TokenConverter implements AttributeConverter<String, byte[]> {
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    @Override
    public byte[] convertToDatabaseColumn(String s) {
        return Hex.decode(s.replace("-",""));
    }

    @Override
    public String convertToEntityAttribute(byte[] b) {
        byte[] hexChars = new byte[b.length * 2];
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        String s = new String(hexChars).toLowerCase();
        StringBuilder token = new StringBuilder(s);
        token.insert(8,'-');
        token.insert(13,'-');
        token.insert(18,'-');
        token.insert(23,'-');
        return token.toString();
    }
}
