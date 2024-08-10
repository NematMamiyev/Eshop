package az.orient.eshopspring2.repository;

import az.orient.eshopspring2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByActive(Integer active);

    Category findByIdAndActive(Long id, Integer active);

    boolean existsCategoryByNameAndActive(String name, Integer active);

    boolean existsCategoryByNameAndActiveAndIdNot(String name, Integer active, Long id);
}
