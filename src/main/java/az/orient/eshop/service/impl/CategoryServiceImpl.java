package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqCategory;
import az.orient.eshop.dto.response.RespCategory;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Category;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.CategoryMapper;
import az.orient.eshop.repository.CategoryRepository;
import az.orient.eshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Response<RespCategory> addCategory(ReqCategory reqCategory) {
        Response<RespCategory> response = new Response<>();
        try {
            String name= reqCategory.getName();
            if (name == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name not found");
            }
            boolean uniqueName = categoryRepository.existsCategoryByNameAndActive(name,EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            Category category = categoryMapper.toCategory(reqCategory);
            categoryRepository.save(category);
            RespCategory respCategory = categoryMapper.toRespCategory(category);
            response.setT(respCategory);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<List<RespCategory>> categoryList() {
        Response<List<RespCategory>> response = new Response<>();
        try {
            List<Category> categoryList = categoryRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (categoryList.isEmpty()){
                throw new EshopException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            List<RespCategory> respBrandList = categoryMapper.toRespCategoryList(categoryList);
            response.setT(respBrandList);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespCategory> getCategoryById(Long id) {
        Response<RespCategory> response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "id not found");
            }
            Category category = getCategory(id);
            RespCategory respCategory = categoryMapper.toRespCategory(category);
            response.setT(respCategory);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespCategory> updateCategory(Long id ,ReqCategory reqCategory) {
        Response<RespCategory> response = new Response<>();
        try {
            String name = reqCategory.getName();
            if (id == null || name == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id or name is invalid data");
            }
            Category category = getCategory(id);
            boolean uniqueName = categoryRepository.existsCategoryByNameAndActiveAndIdNot(name,EnumAvailableStatus.ACTIVE.getValue(), id);
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            categoryMapper.updateCategoryFromReqCategory(category,reqCategory);
            categoryRepository.save(category);
            RespCategory respCategory = categoryMapper.toRespCategory(category);
            response.setT(respCategory);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response deleteCategory(Long id) {
        Response response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Id is invalid data");
            }
            Category category = getCategory(id);
            category.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            categoryRepository.save(category);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    public Category getCategory(Long categoryId){
        Category category = categoryRepository.findByIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.getValue());
        if (category == null){
            throw new EshopException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
        }
        return category;
    }
}
