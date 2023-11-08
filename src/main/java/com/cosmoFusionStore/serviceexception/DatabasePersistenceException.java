package com.cosmoFusionStore.serviceexception;

public class DatabasePersistenceException extends RuntimeException {

    public DatabasePersistenceException(String msg) {
        super(msg);
    }
}
