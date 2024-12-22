package az.orient.eshop.repository;

import az.orient.eshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findCartByCustomerIdAndActive(Long customerId, Integer active);
}
