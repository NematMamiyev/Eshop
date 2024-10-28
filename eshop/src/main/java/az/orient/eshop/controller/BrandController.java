package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqBrand;
import az.orient.eshop.dto.response.RespBrand;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.securitytoken.UserToken;
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
    public Response<RespBrand> addBrand(@RequestHeader String token, @RequestBody ReqBrand reqBrand){
        return brandService.addBrand(token,reqBrand);
    }

    @GetMapping
    public Response<List<RespBrand>> brandList(@RequestHeader String token){
        return brandService.brandList(token);
    }

    @GetMapping("/{id}")
    public Response<RespBrand> getBrandById(@RequestHeader String token, @PathVariable Long id){
        return brandService.getBrandById(token,id);
    }

    @PutMapping
    public Response<RespBrand> updateBrand(@RequestHeader String token, @RequestBody ReqBrand reqBrand){
        return brandService.updateBrand(token, reqBrand);
    }

    @PutMapping("/{id}")
    public Response deleteBrand(@RequestHeader String token, @PathVariable Long id){
        return brandService.deleteBrand(token,id);
    }
}
