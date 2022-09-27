package com.cooksys.assessment1.group3.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.assessment1.group3.entities.User;
import com.cooksys.assessment1.group3.repositories.HashtagRepository;
import com.cooksys.assessment1.group3.repositories.UserRepository;
import com.cooksys.assessment1.group3.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final UserRepository userRepository;

    private final HashtagRepository hashtagRepository;

    @Override
    public boolean validateHashtagExists(String label) {
        return hashtagRepository.findHashtagByLabel(label).isPresent();
    }

    @Override
    public boolean validateUsernameExists(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        if (!optionalUser.isEmpty()) {
            return optionalUser.get().getCredentials().getUsername().equals(username);
        }
        return false;
    }

    @Override
    public boolean validateUsernameAvailable(String username) {
        return !validateUsernameExists(username);
    }
}
