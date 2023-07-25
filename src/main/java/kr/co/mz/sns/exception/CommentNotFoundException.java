package kr.co.mz.sns.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String message) {
        super(message);
    }
}
