package az.orient.eshop.jwttoken;
import az.orient.eshop.entity.Customer;
import az.orient.eshop.entity.Employee;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmailAndActive(email, EnumAvailableStatus.ACTIVE.getValue());
        if (employee != null) {
            return JwtUserDetails.create(employee);
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}

