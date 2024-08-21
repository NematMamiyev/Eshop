package az.orient.eshop.repository;

import az.orient.eshop.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod , Long> {

    boolean existsPaymentMethodByNameAndActive(String name, Integer active);

    List<PaymentMethod> findAllByActive(Integer active);

    PaymentMethod findPaymentMethodByIdAndActive(Long id, Integer active);

}
