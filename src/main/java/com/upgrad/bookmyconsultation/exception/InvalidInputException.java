package com.upgrad.bookmyconsultation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InvalidInputException extends Exception {
    private List<String> attributeNames;

    public InvalidInputException(String message) {
        super(message);
    }
}
