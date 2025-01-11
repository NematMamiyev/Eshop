package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqBrand;
import az.orient.eshop.dto.response.RespBrand;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.BrandService;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PostMapping
    public Response<RespBrand> addBrand(@RequestBody @Valid ReqBrand reqBrand){
        return brandService.addBrand(reqBrand);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<List<RespBrand>> brandList(){
        return brandService.brandList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Response<RespBrand> getBrandById(@PathVariable @NotNull(message = "Id is required") Long id){
        return brandService.getBrandById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Response<RespBrand> updateBrand(@PathVariable @NotNull(message = "Id is required") Long id,@RequestBody @Valid ReqBrand reqBrand){
        return brandService.updateBrand(id, reqBrand);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public RespStatus deleteBrand(@PathVariable @NotNull(message = "Id is required") Long id){
        return brandService.deleteBrand(id);
    }
}
