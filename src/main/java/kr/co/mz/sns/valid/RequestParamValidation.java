package kr.co.mz.sns.valid;

import kr.co.mz.sns.dto.PostDto;
import kr.co.mz.sns.exception.InvalidPathVariableFormatException;

public class RequestParamValidation {

    public static void validate(PostDto postDto) {
        if (postDto.getContent() != null) {
            throw new InvalidPathVariableFormatException("Please enter the post content");
        }
    }
}
