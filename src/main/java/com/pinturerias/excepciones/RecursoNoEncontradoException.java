package com.pinturerias.excepciones;


public class RecursoNoEncontradoException extends ApiException {

    public RecursoNoEncontradoException(String mensaje) {
        super(404, mensaje);
    }
}