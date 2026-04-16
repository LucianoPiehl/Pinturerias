package com.pinturerias.excepciones;


public class RecursoNoEncontradoException extends ExcepcionApi {

    public RecursoNoEncontradoException(String mensaje) {
        super(404, mensaje);
    }
}


