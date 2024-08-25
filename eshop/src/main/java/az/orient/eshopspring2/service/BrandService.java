package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqBrand;
import az.orient.eshopspring2.dto.response.RespBrand;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface BrandService {
    Response<RespBrand> addBrand(ReqBrand reqBrand);

    Response<List<RespBrand>> brandList();

    Response<RespBrand> getBrandById(Long id);

    Response<RespBrand> updateBrand(ReqBrand reqBrand);

    Response deleteBrand(Long id);
}
