package az.orient.eshopspring2.repository;

import az.orient.eshopspring2.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    List<Warehouse> findAllByActive(Integer active);
    Warehouse findWarehouseByIdAndActive(Long id, Integer active);
    boolean existsWarehouseByNameAndActive(String name, Integer active);
    boolean existsWarehouseByNameAndActiveAndIdNot(String name, Integer active,Long id);
}
