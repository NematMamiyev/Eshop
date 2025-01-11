package az.orient.eshop.security;
import az.orient.eshop.entity.Customer;
import az.orient.eshop.entity.Employee;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Role;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.EmployeeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    @Setter
    @Getter
    private Role role;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(role==Role.ADMIN|| role == Role.SUPER_ADMIN || role ==Role.OPERATOR) {
            Employee employee =employeeRepository.findByEmailAndActive(username,EnumAvailableStatus.ACTIVE.getValue());
            if (employee == null){
                throw new UsernameNotFoundException("Admin Username "+ username+ "not found");
            }
            return createUserDetails(employee.getEmail(),employee.getPassword(),role);
        } else if(role == Role.CUSTOMER) {
            Customer customer = customerRepository.findByEmailAndActive(username,EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null){
                throw new UsernameNotFoundException("Customer Email "+ username+ "not found");
            }
            return createUserDetails(customer.getEmail(),customer.getPassword(),role);
        }
        throw new EshopException(ExceptionConstants.USERNAME_OR_PASSWORD_ARE_INCORRECT,"Username or Email " + username + " not found");
    }

    private UserDetails createUserDetails(String email, String password, Role role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return new User(email, password, authorities);
    }

}

