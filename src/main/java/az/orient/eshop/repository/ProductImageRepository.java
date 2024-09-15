package az.orient.eshop.repository;

import az.orient.eshop.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Set<ProductImage> findProductImageByProductDetailsIdAndActive(Long productDetailsId, Integer active);
}
