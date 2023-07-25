package kr.co.mz.sns.exception;

public class InvalidPathVariableFormatException extends RuntimeException{
    public InvalidPathVariableFormatException(String message) {
        super(message);
    }
    public InvalidPathVariableFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
