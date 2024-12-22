package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqWarehouse;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.RespWarehouse;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.WarehouseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public Response<RespWarehouse> addWarehouse(@RequestBody @Valid ReqWarehouse reqWarehouse){
        return warehouseService.addWarehouse(reqWarehouse);
    }

    @GetMapping
    public Response<List<RespWarehouse>> getWarehouseList(){
        return warehouseService.getWarehouseList();
    }

    @GetMapping("/{id}")
    public Response<RespWarehouse>  getWarehouseById(@PathVariable @NotNull(message = "Id is required") Long id){
        return warehouseService.getWarehouseById(id);
    }

    @PutMapping("/{id}")
    public Response<RespWarehouse> updateWarehouse(@PathVariable @NotNull(message = "Id is required") Long id,@RequestBody @Valid ReqWarehouse reqWarehouse){
        return warehouseService.updateWarehouse(id, reqWarehouse);
    }

    @DeleteMapping("/{id}")
    public RespStatus deleteWarehouse(@PathVariable @NotNull(message = "Id is required") Long id){
        return warehouseService.deleteWarehouse(id);
    }
}
