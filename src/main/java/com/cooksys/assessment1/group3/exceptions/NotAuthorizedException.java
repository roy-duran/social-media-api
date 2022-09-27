package com.cooksys.assessment1.group3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 803705141740136418L;

    private String message;

}
