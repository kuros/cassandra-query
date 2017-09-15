package com.github.kuros.cassandra.query.exception;

public class StaticMetaModelException extends RuntimeException {

    public StaticMetaModelException(final String message) {
        super(message);
    }

    public StaticMetaModelException(final Throwable cause) {
        super(cause);
    }
}
