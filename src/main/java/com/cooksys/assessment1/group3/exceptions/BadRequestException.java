package com.cooksys.assessment1.group3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 7393304358701265809L;

    private String message;

}
