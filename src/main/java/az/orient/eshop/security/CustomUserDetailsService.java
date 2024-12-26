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
        if(role==Role.ADMIN) {
            Employee employee =employeeRepository.findByEmailAndActive(username,EnumAvailableStatus.ACTIVE.getValue());
            if (employee == null){
                throw new UsernameNotFoundException("Admin Username "+ username+ "not found");
            }
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
            return new User(employee.getEmail(), employee.getPassword(), authorities);
        } else if(role == Role.CUSTOMER) {
            Customer customer = customerRepository.findByEmailAndActive(username,EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null){
                throw new UsernameNotFoundException("Customer Email "+ username+ "not found");
            }
            SimpleGrantedAuthority customerAuthority = new SimpleGrantedAuthority(Role.CUSTOMER.toString());
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(customerAuthority);
            return new User(customer.getEmail(), customer.getPassword(), authorities);
        }
        return null;
    }

}

