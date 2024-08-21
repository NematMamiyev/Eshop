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
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping("/create")
    public Response<RespWarehouse> addWarehouse(@RequestBody ReqWarehouse reqWarehouse){
        return warehouseService.addWarehouse(reqWarehouse);
    }

    @GetMapping("/list")
    public Response<List<RespWarehouse>> getWarehouseList(){
        return warehouseService.getWarehouseList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespWarehouse>  getWarehouseById(@PathVariable Long id){
        return warehouseService.getWarehouseById(id);
    }

    @PutMapping("/update")
    public Response<RespWarehouse> updateWarehouse(@RequestBody ReqWarehouse reqWarehouse){
        return warehouseService.updateWarehouse(reqWarehouse);
    }

    @PutMapping("/delete/{id}")
    public Response deleteWarehouse(@PathVariable Long id){
        return warehouseService.deleteWarehouse(id);
    }
}
