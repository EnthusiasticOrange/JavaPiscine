package edu.school21.printer.app;

public class UnknownColorException extends RuntimeException {
    public UnknownColorException(String msg) {
        super(msg);
    }
}