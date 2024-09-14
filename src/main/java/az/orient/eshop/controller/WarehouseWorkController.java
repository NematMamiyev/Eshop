package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespWareHouseWork;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.WarehouseWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehousework")
public class WarehouseWorkController {
    private final WarehouseWorkService warehouseWorkService;

    @GetMapping("/works")
    public Response<List<RespWareHouseWork>> works(){
        return warehouseWorkService.works();
    }

    @PutMapping("/handlework/{id}")
    public Response<RespWareHouseWork> handleWork(@PathVariable Long id){
        return warehouseWorkService.handleWork(id);
    }
}
