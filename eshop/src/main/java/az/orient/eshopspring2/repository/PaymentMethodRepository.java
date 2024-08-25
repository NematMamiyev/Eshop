package az.orient.eshopspring2.repository;

import az.orient.eshopspring2.entity.PaymentMethod;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod , Long> {

    boolean existsPaymentMethodByNameAndActive(String name, Integer active);

    List<PaymentMethod> findAllByActive(Integer active);

    PaymentMethod findPaymentMethodByIdAndActive(Long id, Integer active);

}
