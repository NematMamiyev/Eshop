package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqBrand;
import az.orient.eshop.dto.response.RespBrand;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface BrandService {
    Response<RespBrand> addBrand(ReqBrand reqBrand);

    Response<List<RespBrand>> brandList();

    Response<RespBrand> getBrandById(Long id);

    Response<RespBrand> updateBrand(Long id, ReqBrand reqBrand);

    Response deleteBrand(Long id);
}
