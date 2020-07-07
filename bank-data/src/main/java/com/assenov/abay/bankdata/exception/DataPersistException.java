package com.assenov.abay.bankdata.exception;

public class DataPersistException extends RuntimeException  {

    public DataPersistException(String message) {
        super(message);
    }

    public DataPersistException(String message, Throwable cause) {
        super(message, cause);
    }
}
