package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqCategory;
import az.orient.eshopspring2.dto.response.RespCategory;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface CategoryService {
    Response<RespCategory> addCategory(ReqCategory reqCategory);

    Response<List<RespCategory>> categoryList();

    Response<RespCategory> getCategoryById(Long id);

    Response<RespCategory> updateCategory(ReqCategory reqCategory);

    Response deleteCategory(Long id);
}
