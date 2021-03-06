package com.example.checklists.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ChecklistNotFoundException extends RuntimeException {
    public ChecklistNotFoundException(String message) {
        super(message);
    }

    public ChecklistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
