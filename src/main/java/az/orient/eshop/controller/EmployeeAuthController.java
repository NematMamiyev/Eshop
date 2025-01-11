package az.orient.eshop.controller;

import az.orient.eshop.dto.request.LoginRequest;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.EmployeeAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth/employee")
@RequiredArgsConstructor
public class EmployeeAuthController {

    private final EmployeeAuthService employeeAuthService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public Response<String> login(@RequestBody @Valid LoginRequest loginRequest){
       return employeeAuthService.login(loginRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    public Response<String> logout(@RequestHeader("Authorization") String token) {
       return employeeAuthService.logout(token);
    }
}
