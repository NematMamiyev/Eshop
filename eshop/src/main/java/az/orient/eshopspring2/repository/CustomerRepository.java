package az.orient.eshopspring2.repository;

import az.orient.eshopspring2.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmailIgnoreCaseAndActiveAndIdNot(String email, Integer active, Long id);

    boolean existsCustomerByPhoneIgnoreCaseAndActiveAndIdNot(String phone, Integer active, Long id);

    boolean existsCustomerByEmailIgnoreCaseAndActive(String email, Integer active);

    boolean existsCustomerByPhoneIgnoreCaseAndActive(String phone, Integer active);

    List<Customer> findAllByActive(Integer active);

    Customer findCustomerByIdAndActive(Long id, Integer active);
}
