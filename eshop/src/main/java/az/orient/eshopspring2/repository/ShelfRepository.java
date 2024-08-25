package az.orient.eshopspring2.repository;

import az.orient.eshopspring2.entity.Brand;
import az.orient.eshopspring2.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    List<Shelf> findAllByActive(Integer active);
    Shelf findByIdAndActive(Long id,Integer active);
}
