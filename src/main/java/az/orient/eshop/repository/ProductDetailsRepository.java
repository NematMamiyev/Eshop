package az.orient.eshop.repository;

import az.orient.eshop.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails,Long> {
    ProductDetails findProductDetailsByIdAndActive(Long productDetailsId, Integer active);
    List<ProductDetails> findProductDetailsByActive(Integer active);
    boolean existsProductDetailsByIdAndActive(Long productDetailsId, Integer active);
}
