package az.orient.eshop.repository;

import az.orient.eshop.entity.Product;
import az.orient.eshop.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByActive(Integer active);

    Product findProductByIdAndActive(Long id,Integer active);

}
