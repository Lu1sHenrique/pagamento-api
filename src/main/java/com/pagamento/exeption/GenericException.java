package com.pagamento.exeption;

public class GenericException extends RuntimeException{
    public GenericException() {
    }

    public GenericException(String message) {
        super(message);
    }
}