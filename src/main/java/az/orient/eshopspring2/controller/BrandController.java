package az.orient.eshopspring2.controller;

import az.orient.eshopspring2.dto.request.ReqBrand;
import az.orient.eshopspring2.dto.response.RespBrand;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.service.BrandService;
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
