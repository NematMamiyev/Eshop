package az.orient.eshop.repository;

import az.orient.eshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findOrderByCustomerIdAndActive(Long customerId,Integer active);
    Order findOrderByIdAndActive(Long orderId,Integer active);
}
