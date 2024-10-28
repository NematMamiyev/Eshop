package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqBrand;
import az.orient.eshop.dto.response.RespBrand;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface BrandService {
    Response<RespBrand> addBrand(String token, ReqBrand reqBrand);

    Response<List<RespBrand>> brandList(String token);

    Response<RespBrand> getBrandById(String token, Long id);

    Response<RespBrand> updateBrand(String token, ReqBrand reqBrand);

    Response deleteBrand(String token, Long id);
}
