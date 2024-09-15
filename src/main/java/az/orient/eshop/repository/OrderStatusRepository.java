package az.orient.eshop.repository;

import az.orient.eshop.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusRepository extends JpaRepository<OrderStatus,Long> {
    List<OrderStatus> findOrderStatusByOrderIdAndActive(Long orderId, Integer active);
}
