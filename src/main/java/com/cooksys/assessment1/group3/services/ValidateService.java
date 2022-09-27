package com.cooksys.assessment1.group3.services;

public interface ValidateService {

    boolean validateHashtagExists(String label);

    boolean validateUsernameExists(String username);

    boolean validateUsernameAvailable(String username);

}
