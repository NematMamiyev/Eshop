package az.orient.eshopspring2.repository;

import az.orient.eshopspring2.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    boolean existsSubcategoryByNameAndActive(String name, Integer active);

    boolean existsSubcategoryByNameAndActiveAndIdNot(String name, Integer active, Long id);

    List<Subcategory> findAllByActive(Integer active);

    Subcategory findSubcategoryByIdAndActive(Long id, Integer active);
}
