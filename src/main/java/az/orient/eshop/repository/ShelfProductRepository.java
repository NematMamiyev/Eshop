package az.orient.eshop.repository;

import az.orient.eshop.entity.ShelfProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShelfProductRepository extends JpaRepository<ShelfProductDetails, Long> {

    ShelfProductDetails findShelfProductByShelfIdAndProductDetailsIdAndActive(Long shelfId, Long productId, Integer active);

    @Query("""
        SELECT CASE WHEN COUNT(spd) > 0 THEN true ELSE false END
        FROM ShelfProductDetails spd
        WHERE spd.productDetails.id = :productDetailsId
          AND spd.shelf.warehouse.id = :warehouseId
    """)
    boolean existsByProductDetailsInWarehouse(
            @Param("productDetailsId") Long productDetailsId,
            @Param("warehouseId") Long warehouseId
    );
}
