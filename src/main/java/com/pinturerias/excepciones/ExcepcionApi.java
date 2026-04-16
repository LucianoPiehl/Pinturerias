package com.pinturerias.excepciones;


public class ExcepcionApi extends RuntimeException {

    private final int status;

    public ExcepcionApi(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}


