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
        boolean uniqueName = categoryRepository.existsCategoryByNameAndActive(reqCategory.getName(), EnumAvailableStatus.ACTIVE.getValue());
        if (uniqueName) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
        }
        Category category = categoryMapper.toCategory(reqCategory);
        categoryRepository.save(category);
        response.setT(categoryMapper.toRespCategory(category));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<List<RespCategory>> categoryList() {
        Response<List<RespCategory>> response = new Response<>();
        List<Category> categoryList = categoryRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
        if (categoryList.isEmpty()) {
            throw new EshopException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
        }
        response.setT(categoryMapper.toRespCategoryList(categoryList));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespCategory> getCategoryById(Long id) {
        Response<RespCategory> response = new Response<>();
        Category category = getCategory(id);
        response.setT(categoryMapper.toRespCategory(category));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespCategory> updateCategory(Long id, ReqCategory reqCategory) {
        Response<RespCategory> response = new Response<>();
        Category category = getCategory(id);
        boolean uniqueName = categoryRepository.existsCategoryByNameAndActiveAndIdNot(reqCategory.getName(), EnumAvailableStatus.ACTIVE.getValue(), id);
        if (uniqueName) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
        }
        categoryMapper.updateCategoryFromReqCategory(category, reqCategory);
        categoryRepository.save(category);
        response.setT(categoryMapper.toRespCategory(category));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public RespStatus deleteCategory(Long id) {
        Category category = getCategory(id);
        category.setActive(EnumAvailableStatus.DEACTIVATED.getValue());
        categoryRepository.save(category);
        return RespStatus.getSuccessMessage();
    }

    public Category getCategory(Long categoryId) {
        Category category = categoryRepository.findByIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.getValue());
        if (category == null) {
            throw new EshopException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
        }
        return category;
    }
}
