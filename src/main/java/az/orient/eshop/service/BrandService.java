package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqBrand;
import az.orient.eshop.dto.response.RespBrand;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BrandService {
    Response<RespBrand> addBrand(ReqBrand reqBrand);

    Response<List<RespBrand>> brandList();

    Response<RespBrand> getBrandById(Long id);

    Response<RespBrand> updateBrand(Long id, ReqBrand reqBrand);

    RespStatus deleteBrand(Long id);

}
