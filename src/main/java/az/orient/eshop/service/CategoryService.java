package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqCategory;
import az.orient.eshop.dto.response.RespCategory;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface CategoryService {
    Response<RespCategory> addCategory(ReqCategory reqCategory);

    Response<List<RespCategory>> categoryList();

    Response<RespCategory> getCategoryById(Long id);

    Response<RespCategory> updateCategory(Long id,ReqCategory reqCategory);

    RespStatus deleteCategory(Long id);
}
