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
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    @PostMapping("/create")
    public Response<RespBrand> addBrand(@RequestBody ReqBrand reqBrand){
        return brandService.addBrand(reqBrand);
    }

    @GetMapping("/list")
    public Response<List<RespBrand>> brandList(){
        return brandService.brandList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespBrand> getBrandById(@PathVariable Long id){
        return brandService.getBrandById(id);
    }

    @PutMapping("/update")
    public Response<RespBrand> updateBrand(@RequestBody ReqBrand reqBrand){
        return brandService.updateBrand(reqBrand);
    }

    @PutMapping("/delete/{id}")
    public Response deleteBrand(@PathVariable Long id){
        return brandService.deleteBrand(id);
    }
}
