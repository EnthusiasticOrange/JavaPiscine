package edu.school21.numbers;

public class IllegalNumberException extends RuntimeException {
    public IllegalNumberException() {
        super();
    }

    public IllegalNumberException(String msg) {
        super(msg);
    }
}
