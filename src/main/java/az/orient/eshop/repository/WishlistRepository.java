package az.orient.eshop.repository;

import az.orient.eshop.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {


    List<Wishlist> findAllByActive(Integer active);

    Wishlist findWishlistByProductIdAndCustomerIdAndActive(Long productId, Long customerId, Integer active);

    List<Wishlist> findWishlistByCustomerIdAndActive(Long customerId, Integer active);
}
