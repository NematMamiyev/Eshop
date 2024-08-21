package az.orient.eshop.repository;

import az.orient.eshop.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    List<Shelf> findAllByActive(Integer active);
    Shelf findByIdAndActive(Long id,Integer active);
}
