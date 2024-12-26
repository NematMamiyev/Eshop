package az.orient.eshop.service;

import az.orient.eshop.dto.request.LoginRequest;
import az.orient.eshop.dto.response.Response;

public interface EmployeeAuthService {
    Response<String> login(LoginRequest loginRequest);
    Response<String> logout(String token);
}
