package az.orient.eshop.repository;

import az.orient.eshop.entity.ProductVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ProductVideoRepository extends JpaRepository<ProductVideo, Long> {
    Set<ProductVideo> findProductVideoByProductDetailsIdAndActive(Long productDetailsId, int active);
    ProductVideo findProductVideoByIdAndActive(Long videoId, Integer active);
    @Transactional
    @Modifying
    @Query("UPDATE ProductVideo pi SET pi.active = 0 WHERE pi.productDetails.id = :productDetailsId")
    void deactivateProductVideoByProductDetailsId(@Param("productDetailsId") Long productDetailsId);
}
