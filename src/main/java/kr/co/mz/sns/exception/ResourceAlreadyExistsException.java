package kr.co.mz.sns.exception;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(@NotNull @NotEmpty String message) {
        super(message);
    }
}
