package ug.visituganda.visituganda.service;

import ug.visituganda.visituganda.dto.LoginRequest;
import ug.visituganda.visituganda.dto.response.AuthenticationResponse;
import ug.visituganda.visituganda.dto.response.LoginResponse;

public interface LoginService {
    AuthenticationResponse login(LoginRequest request);
}
