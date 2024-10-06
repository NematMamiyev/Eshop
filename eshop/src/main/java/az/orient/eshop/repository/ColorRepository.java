package az.orient.eshop.repository;

import az.orient.eshop.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long> {


    List<Color> findAllByActive(Integer active);

    Color findByIdAndActive(Long id, Integer active);

    boolean existsColorByNameAndActive(String name, Integer active);
    boolean existsColorByNameAndActiveAndIdNot(String name, Integer active, Long id);
}
