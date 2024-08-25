package org.demo.cp.exceptions;

public class InsufficientShareException extends RuntimeException{
    private static final String MESSAGE = "Customer [id=%d] doesn't have enough shares to complete the transaction";

    public InsufficientShareException(Integer id) {
        super(MESSAGE.formatted(id));
    }
}
