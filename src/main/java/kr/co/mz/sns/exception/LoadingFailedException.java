package kr.co.mz.sns.exception;

public class LoadingFailedException extends RuntimeException {

    public LoadingFailedException(String message) {
        super(message);
    }
}
