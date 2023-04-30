package com.rungroup.runApp.service;

import com.rungroup.runApp.dto.RegistrationDto;
import com.rungroup.runApp.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
