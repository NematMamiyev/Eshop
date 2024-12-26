package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.LoginRequest;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Employee;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.EmployeeRepository;
import az.orient.eshop.security.CustomUserDetailsService;
import az.orient.eshop.security.JwtGenerator;
import az.orient.eshop.service.EmployeeAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmployeeAuthServiceImpl implements EmployeeAuthService {

    @Value("${jwt.access.expiration}")
    private long jwtAccessExpiration;

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final EmployeeRepository employeeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Response<String> login(LoginRequest loginRequest) {
        Response<String> response = new Response<>();
        Employee employee = employeeRepository.findByEmailAndActive(loginRequest.getMail(), EnumAvailableStatus.ACTIVE.getValue());
        if (employee == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Email or password is incorrect");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), employee.getPassword())) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"User not found or password incorrect");
        }
        customUserDetailsService.setRole(employee.getRole());
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getPassword()));
        String token = jwtGenerator.generateTokenEmployee(employee);
        response.setT(token);
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<String> logout(String token) {
        Response<String> response = new Response<>();
        String jwtToken = token.substring(7);
        redisTemplate.opsForValue().set(jwtToken, "blacklisted", jwtAccessExpiration, TimeUnit.MILLISECONDS);
        response.setStatus(RespStatus.getSuccessMessage());
        response.setT("Logged out successfully!");
        return response;
    }
}
