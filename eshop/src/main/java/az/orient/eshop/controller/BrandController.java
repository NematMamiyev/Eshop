package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqBrand;
import az.orient.eshop.dto.response.RespBrand;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public Response<RespBrand> addBrand(@RequestBody ReqBrand reqBrand){
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

    @PutMapping
    public Response<RespBrand> updateBrand(@RequestBody ReqBrand reqBrand){
        return brandService.updateBrand(reqBrand);
    }

    @PutMapping("/{id}")
    public Response deleteBrand(@PathVariable Long id){
        return brandService.deleteBrand(id);
    }
}
