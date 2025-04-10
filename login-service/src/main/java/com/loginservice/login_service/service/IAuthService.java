package com.loginservice.login_service.service;

import com.loginservice.login_service.dto.AuthDTO;
import com.loginservice.login_service.model.User;

public interface IAuthService {
    User login(AuthDTO auth);
}
