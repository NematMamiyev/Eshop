package az.orient.eshopspring2.service.impl;

import az.orient.eshopspring2.dto.request.ReqCategory;
import az.orient.eshopspring2.dto.response.RespCategory;
import az.orient.eshopspring2.dto.response.RespStatus;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.entity.Category;
import az.orient.eshopspring2.enums.EnumAvailableStatus;
import az.orient.eshopspring2.exception.EshopException;
import az.orient.eshopspring2.exception.ExceptionConstants;
import az.orient.eshopspring2.repository.CategoryRepository;
import az.orient.eshopspring2.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
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
            Category category = Category.builder()
                    .id(reqCategory.getId())
                    .name(reqCategory.getName())
                    .build();
            categoryRepository.save(category);
            RespCategory respCategory = convert(category);
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
            List<RespCategory> respBrandList = categoryList.stream().map(this::convert).toList();
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
            Category category = categoryRepository.findByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (category == null){
                throw new EshopException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            RespCategory respCategory = convert(category);
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
    public Response<RespCategory> updateCategory(ReqCategory reqCategory) {
        Response<RespCategory> response = new Response<>();
        try {
            Long id = reqCategory.getId();
            String name = reqCategory.getName();
            if (id == null || name == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id or name is invalid data");
            }
            Category category = categoryRepository.findByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (category == null){
                throw new EshopException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            boolean uniqueName = categoryRepository.existsCategoryByNameAndActiveAndIdNot(name,EnumAvailableStatus.ACTIVE.getValue(), id);
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            category.setName(reqCategory.getName());
            categoryRepository.save(category);
            RespCategory respCategory = convert(category);
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
            Category category = categoryRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (category == null){
                throw new EshopException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
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

    private RespCategory convert(Category category){
        return RespCategory.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
