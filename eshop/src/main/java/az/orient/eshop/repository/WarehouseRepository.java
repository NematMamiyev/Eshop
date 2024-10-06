package az.orient.eshop.repository;

import az.orient.eshop.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    List<Warehouse> findAllByActive(Integer active);
    Warehouse findWarehouseByIdAndActive(Long id, Integer active);
    boolean existsWarehouseByNameAndActive(String name, Integer active);
    boolean existsWarehouseByNameAndActiveAndIdNot(String name, Integer active,Long id);
}
