package az.orient.eshop.security;

import az.orient.eshop.entity.Customer;
import az.orient.eshop.entity.Employee;
import az.orient.eshop.enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private long jwtAccessExpiration;

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateTokenEmployee(Employee employee) {
        return Jwts.builder()
                .setSubject(employee.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtAccessExpiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .claim("role", employee.getRole())
                .claim("id", employee.getId())
                .compact();
    }

    public String generateTokenCustomer(Customer customer) {
        return Jwts.builder()
                .setSubject(customer.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtAccessExpiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .claim("role", Role.CUSTOMER)
                .claim("id", customer.getId())
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRoleFromJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role")
                .toString();
    }

    public Long getId(String token) {
        Object id = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id");
        if (id instanceof Number) {
            return ((Number) id).longValue();
        } else {
            throw new IllegalArgumentException("ID is not a valid number");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT token is not valid " + token);
        }
    }
}
