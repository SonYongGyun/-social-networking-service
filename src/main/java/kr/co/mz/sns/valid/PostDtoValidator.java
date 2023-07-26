package kr.co.mz.sns.valid;

import kr.co.mz.sns.dto.FindPostDto;
import kr.co.mz.sns.exception.InvalidPathVariableFormatException;

public class PostDtoValidator {

    public static void validate(FindPostDto findPostDto) {
        if (findPostDto.getContent() == null) {
            throw new InvalidPathVariableFormatException("Please enter the post content");
        }
    }
}
