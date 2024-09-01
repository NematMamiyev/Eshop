package az.orient.eshop.repository;

import az.orient.eshop.entity.Cart;
import az.orient.eshop.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findAllByActive(Integer active);

    Cart findCartByProductIdAndCustomerIdAndActive(Long productId, Long customerId, Integer active);

    List<Cart> findCartByCustomerIdAndActive(Long customerId, Integer active);
}
