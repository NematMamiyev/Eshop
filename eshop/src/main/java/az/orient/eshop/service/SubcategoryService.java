package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqSubcategory;
import az.orient.eshop.dto.response.RespSubcategory;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface SubcategoryService{
    Response<RespSubcategory> addSubcategory(ReqSubcategory reqSubcategory);

    Response<List<RespSubcategory>> getSubcategoryList();

    Response<RespSubcategory> getSubcategoryById(Long id);

    Response<RespSubcategory> updateSubcategory(Long id, ReqSubcategory reqSubcategory);

    Response deleteSubcategory(Long id);
}
