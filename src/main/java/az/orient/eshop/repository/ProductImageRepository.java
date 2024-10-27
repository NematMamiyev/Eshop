package az.orient.eshop.repository;

import az.orient.eshop.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Set<ProductImage> findProductImageByProductDetailsIdAndActive(Long productDetailsId, Integer active);
    ProductImage findProductImageByIdAndActive(Long imageId, Integer active);
    @Transactional
    @Modifying
    @Query("UPDATE ProductImage pi SET pi.active = 0 WHERE pi.productDetails.id = :productDetailsId")
    void deactivateProductImagesByProductDetailsId(@Param("productDetailsId") Long productDetailsId);
}
