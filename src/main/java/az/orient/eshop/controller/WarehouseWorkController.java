package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespWareHouseWork;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.WarehouseWorkService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouseworks")
public class WarehouseWorkController {
    private final WarehouseWorkService warehouseWorkService;

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @GetMapping
    public Response<List<RespWareHouseWork>> works(){
        return warehouseWorkService.works();
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PutMapping("/handlework/{id}")
    public Response<RespWareHouseWork> handleWork(@PathVariable @NotNull(message = "Id is required") Long id){
        return warehouseWorkService.handleWork(id);
    }
}
