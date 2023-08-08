package kr.co.mz.sns.exception;

public class NoSuchStateException extends IllegalArgumentException {
    public NoSuchStateException(String s) {
        super(s);
    }
}
