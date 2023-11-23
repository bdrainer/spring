package org.bwg.rxmongodb.model.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String id) {
        super("RecordNotFound:" + id + " is not found.");
    }
}
