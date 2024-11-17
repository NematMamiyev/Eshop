package az.orient.eshop.controller;

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
import az.orient.eshop.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth/employee")
@RequiredArgsConstructor
public class EmployeeAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmployeeRepository employeeRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/login")
    public Response<String> login(@RequestBody LoginRequest loginRequest){
        Response<String> response = new Response<>();
        try {
            Employee employee = employeeRepository.findEmployeeByEmailAndPasswordAndActive(loginRequest.getMail(), loginRequest.getPassword(), EnumAvailableStatus.ACTIVE.getValue());
            if (employee == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Email or password is incorrect");
            }
            customUserDetailsService.setRole(employee.getRole());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getMail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication, employee.getRole().toString());
            response.setT(token);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    @PostMapping("/logout")
    public Response<String> logout(@RequestHeader("Authorization") String token) {
        Response<String> response = new Response<>();
        try {
            String jwtToken = token.substring(7);
            redisTemplate.opsForValue().set(jwtToken, "blacklisted", SecurityConstants.JWT_EXPIRATION, TimeUnit.MILLISECONDS);
            response.setStatus(RespStatus.getSuccessMessage());
            response.setT("Logged out successfully!");
        }catch (Exception ex){
                ex.printStackTrace();
            }
            return response;
    }
}
