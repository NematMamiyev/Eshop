package az.orient.eshop.repository;

import az.orient.eshop.entity.ShelfProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfProductRepository extends JpaRepository<ShelfProduct, Long> {

    boolean existsShelfProductByProductDetailsIdAndWarehouseIdAndActive(Long producId,Long warehouseId, Integer active);
    ShelfProduct findShelfProductByShelfIdAndProductDetailsIdAndActive(Long shelfId,Long productId,Integer active);
}
