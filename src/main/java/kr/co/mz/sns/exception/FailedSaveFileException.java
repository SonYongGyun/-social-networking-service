package kr.co.mz.sns.exception;

public class FailedSaveFileException extends RuntimeException {

    public FailedSaveFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
