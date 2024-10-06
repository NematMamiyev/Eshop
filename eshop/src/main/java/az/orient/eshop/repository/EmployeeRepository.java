package az.orient.eshop.repository;

import az.orient.eshop.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsEmployeeByEmailIgnoreCaseAndActive(String email, Integer active);

    boolean existsEmployeeByPhoneIgnoreCaseAndActive(String phone, Integer active);

    List<Employee> findAllByActive(Integer active);

    Employee findEmployeeByIdAndActive(Long id, Integer active);

    boolean existsEmployeeByEmailIgnoreCaseAndActiveAndIdNot(String email,Integer active,Long id);

    boolean existsEmployeeByPhoneIgnoreCaseAndActiveAndIdNot(String phone,Integer active,Long id);

}
