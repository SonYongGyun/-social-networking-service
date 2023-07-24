package kr.co.mz.sns.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }
}
