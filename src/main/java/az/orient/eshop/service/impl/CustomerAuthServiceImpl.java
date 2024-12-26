package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.LoginRequest;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Customer;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Role;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.security.CustomUserDetailsService;
import az.orient.eshop.security.JwtGenerator;
import az.orient.eshop.service.CustomerAuthService;
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
public class CustomerAuthServiceImpl implements CustomerAuthService {

    @Value("${jwt.access.expiration}")
    private long jwtAccessExpiration;

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisTemplate<String, String> redisTemplate;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Response<String> login(LoginRequest loginRequest) {
        Response<String> response = new Response<>();
        Customer customer = customerRepository.findByEmailAndActive(loginRequest.getMail(),EnumAvailableStatus.ACTIVE.getValue());
        if (customer == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Email or password is incorrect");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"User not found or password incorrect");
        }
        customUserDetailsService.setRole(Role.CUSTOMER);
        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getPassword()));
        String token = jwtGenerator.generateTokenCustomer(customer);
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
