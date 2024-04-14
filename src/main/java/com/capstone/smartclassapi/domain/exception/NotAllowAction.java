package com.capstone.smartclassapi.domain.exception;

public class NotAllowAction extends SecurityException {
    public NotAllowAction(String message) {
        super(message);
    }
}
