package az.orient.eshop.repository;

import az.orient.eshop.entity.ProductVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ProductVideoRepository extends JpaRepository<ProductVideo, Long> {
    ProductVideo findProductVideoByIdAndActive(Long productVideoId,Integer active);
    Set<ProductVideo> findProductVideoByProductDetailsIdAndActive(Long productDetailsId, int active);
}
