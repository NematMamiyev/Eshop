package az.orient.eshopspring2.repository;

import az.orient.eshopspring2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByActive(Integer active);

    Product findProductByIdAndActive(Long id,Integer active);
}
