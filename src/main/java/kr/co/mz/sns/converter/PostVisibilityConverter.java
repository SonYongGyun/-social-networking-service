package kr.co.mz.sns.converter;

import jakarta.persistence.AttributeConverter;
import kr.co.mz.sns.enums.PostVisibility;

public class PostVisibilityConverter implements AttributeConverter<PostVisibility, String> {

    @Override
    public String convertToDatabaseColumn(PostVisibility visibility) {
        return visibility.getCode();
    }

    @Override
    public PostVisibility convertToEntityAttribute(String dbData) {
        return PostVisibility.ofCode(dbData);
    }
}
