package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqWarehouse;
import az.orient.eshop.dto.response.RespWarehouse;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public Response<RespWarehouse> addWarehouse(@RequestBody ReqWarehouse reqWarehouse){
        return warehouseService.addWarehouse(reqWarehouse);
    }

    @GetMapping
    public Response<List<RespWarehouse>> getWarehouseList(){
        return warehouseService.getWarehouseList();
    }

    @GetMapping("/{id}")
    public Response<RespWarehouse>  getWarehouseById(@PathVariable Long id){
        return warehouseService.getWarehouseById(id);
    }

    @PutMapping
    public Response<RespWarehouse> updateWarehouse(@RequestBody ReqWarehouse reqWarehouse){
        return warehouseService.updateWarehouse(reqWarehouse);
    }

    @DeleteMapping("/{id}")
    public Response deleteWarehouse(@PathVariable Long id){
        return warehouseService.deleteWarehouse(id);
    }
}
