package kr.co.mz.sns.exception;

public class InsertFailedException extends RuntimeException{
    public InsertFailedException(String message) {
        super(message);
    }
}