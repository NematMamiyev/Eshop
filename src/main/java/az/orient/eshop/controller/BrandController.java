package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqBrand;
import az.orient.eshop.dto.response.RespBrand;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.BrandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public Response<RespBrand> addBrand(@RequestBody @Valid ReqBrand reqBrand){
        return brandService.addBrand(reqBrand);
    }

    @GetMapping
    public Response<List<RespBrand>> brandList(){
        return brandService.brandList();
    }

    @GetMapping("/{id}")
    public Response<RespBrand> getBrandById(@PathVariable Long id){
        return brandService.getBrandById(id);
    }

    @PutMapping("/{id}")
    public Response<RespBrand> updateBrand(@PathVariable @NotNull(message = "Id is required") Long id,@RequestBody @Valid ReqBrand reqBrand){
        return brandService.updateBrand(id, reqBrand);
    }

    @DeleteMapping("/{id}")
    public Response deleteBrand(@PathVariable @NotNull(message = "Id is required") Long id){
        return brandService.deleteBrand(id);
    }
}
