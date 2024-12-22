package az.orient.eshop.repository;

import az.orient.eshop.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size , Long> {

    boolean existsSizeByNameAndActive(String name, Integer active);

    boolean existsSizeByNameAndActiveAndIdNot(String name, Integer active, Long id);

    boolean existsSizeByIdAndActive(Long id, Integer active);

    List<Size> findAllByActive(Integer active);

    Size findSizeByIdAndActive(Long id, Integer active);
}
