package az.orient.eshop.repository;

import az.orient.eshop.entity.ShelfProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfProductRepository extends JpaRepository<ShelfProductDetails, Long> {

    boolean existsShelfProductByProductDetailsIdAndWarehouseIdAndActive(Long producId,Long warehouseId, Integer active);
    ShelfProductDetails findShelfProductByShelfIdAndProductDetailsIdAndActive(Long shelfId, Long productId, Integer active);
}
