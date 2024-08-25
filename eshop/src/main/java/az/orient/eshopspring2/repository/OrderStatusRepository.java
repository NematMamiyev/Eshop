package az.orient.eshopspring2.repository;

import az.orient.eshopspring2.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    List<OrderStatus> findAllByActive(Integer active);
    OrderStatus findByIdAndActive(Long id, Integer active);
    boolean existsOrderStatusByNameAndActive(String name, Integer active);
    boolean existsOrderStatusByNameAndActiveAndIdNot(String name, Integer active, Long id);
}
