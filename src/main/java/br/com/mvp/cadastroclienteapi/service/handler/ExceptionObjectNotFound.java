package br.com.mvp.cadastroclienteapi.service.handler;

public abstract class ExceptionObjectNotFound extends RuntimeException {
    public ExceptionObjectNotFound(String message) {
        super(message);
    }
}
