package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqSubcategory;
import az.orient.eshopspring2.dto.response.RespSubcategory;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface SubcategoryService{
    Response<RespSubcategory> addSubcategory(ReqSubcategory reqSubcategory);

    Response<List<RespSubcategory>> getSubcategoryList();

    Response<RespSubcategory> getSubcategoryById(Long id);

    Response<RespSubcategory> updateSubcategory(ReqSubcategory reqSubcategory);

    Response deleteSubcategory(Long id);
}
