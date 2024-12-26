package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqShelf;
import az.orient.eshop.dto.response.RespShelf;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ShelfService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shelfs")
public class ShelfController {
    private final ShelfService shelfService;

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PostMapping
    public Response<RespShelf> addShelf(@RequestBody @Valid ReqShelf reqShelf){
        return shelfService.addShelf(reqShelf);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @GetMapping
    public Response<List<RespShelf>> shelfList(){
        return shelfService.shelfList();
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @GetMapping("/{id}")
    public Response<RespShelf> getShelfById(@PathVariable @NotNull(message = "Id is required") Long id){
        return shelfService.getShelfById(id);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PutMapping("/{id}")
    public Response<RespShelf> updateShelf(@PathVariable @NotNull(message = "Id is required") Long id,@RequestBody @Valid ReqShelf reqShelf){
        return shelfService.updateShelf(id,reqShelf);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public RespStatus deleteShelf(@PathVariable @NotNull(message = "Id is required") Long id){
        return shelfService.deleteShelf(id);
    }
}
