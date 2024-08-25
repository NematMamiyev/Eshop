package az.orient.eshopspring2.service.impl;

import az.orient.eshopspring2.dto.request.ReqSubcategory;
import az.orient.eshopspring2.dto.response.RespCategory;
import az.orient.eshopspring2.dto.response.RespStatus;
import az.orient.eshopspring2.dto.response.RespSubcategory;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.entity.Category;
import az.orient.eshopspring2.entity.Subcategory;
import az.orient.eshopspring2.enums.EnumAvailableStatus;
import az.orient.eshopspring2.exception.EshopException;
import az.orient.eshopspring2.exception.ExceptionConstants;
import az.orient.eshopspring2.repository.CategoryRepository;
import az.orient.eshopspring2.repository.SubcategoryRepository;
import az.orient.eshopspring2.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Response<RespSubcategory> addSubcategory(ReqSubcategory reqSubcategory) {
        Response<RespSubcategory> response = new Response<>();
        try {
            String name = reqSubcategory.getName();
            Long categoryId = reqSubcategory.getCategoryId();
            if (name == null || categoryId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Category category = categoryRepository.findByIdAndActive(categoryId, EnumAvailableStatus.ACTIVE.getValue());
            if (category == null) {
                throw new EshopException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            boolean uniqueName = subcategoryRepository.existsSubcategoryByNameAndActive(name, EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            Subcategory subcategory = Subcategory.builder()
                    .name(name)
                    .category(category)
                    .build();
            subcategoryRepository.save(subcategory);
            RespSubcategory respSubcategory = convert(subcategory);
            response.setT(respSubcategory);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }

        return response;
    }

    @Override
    public Response<List<RespSubcategory>> getSubcategoryList() {
        Response<List<RespSubcategory>> response = new Response<>();
        try {
            List<Subcategory> subcategoryList = subcategoryRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (subcategoryList.isEmpty()) {
                throw new EshopException(ExceptionConstants.SUBCATEGORY_NOT_FOUND, "Subcategory is empty");
            }
            List<RespSubcategory> respSubcategoryList = subcategoryList.stream().map(this::convert).toList();
            response.setT(respSubcategoryList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespSubcategory> getSubcategoryById(Long id) {
        Response<RespSubcategory> response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            Subcategory subcategory = subcategoryRepository.findSubcategoryByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (subcategory == null) {
                throw new EshopException(ExceptionConstants.SUBCATEGORY_NOT_FOUND, "Subcategory not found");
            }
            RespSubcategory respSubcategory = convert(subcategory);
            response.setT(respSubcategory);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespSubcategory> updateSubcategory(ReqSubcategory reqSubcategory) {
        Response<RespSubcategory> response = new Response<>();
        try {
            Long id = reqSubcategory.getId();
            String name = reqSubcategory.getName();
            Long categoryId = reqSubcategory.getCategoryId();
            if (id == null || name == null || categoryId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Subcategory subcategory = subcategoryRepository.findSubcategoryByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (subcategory== null){
                throw new EshopException(ExceptionConstants.SUBCATEGORY_NOT_FOUND, "Subcategory not found");
            }
            boolean uniqueName = subcategoryRepository.existsSubcategoryByNameAndActiveAndIdNot(name,EnumAvailableStatus.ACTIVE.getValue(), id);
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Name available in the database");
            }
            Category category = categoryRepository.findByIdAndActive(categoryId,EnumAvailableStatus.ACTIVE.getValue());
            if (category == null){
                throw new EshopException(ExceptionConstants.CATEGORY_NOT_FOUND, "Category not found");
            }
            subcategory.setName(name);
            subcategory.setCategory(category);
            subcategoryRepository.save(subcategory);
            RespSubcategory respSubcategory = convert(subcategory);
            response.setT(respSubcategory);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response deleteSubcategory(Long id) {
        Response response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            Subcategory subcategory = subcategoryRepository.findSubcategoryByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (subcategory == null){
                throw new EshopException(ExceptionConstants.SUBCATEGORY_NOT_FOUND, "Subcategory not found");
            }
            subcategory.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            subcategoryRepository.save(subcategory);
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

    private RespSubcategory convert(Subcategory subcategory) {
        RespCategory respCategory = RespCategory.builder()
                .id(subcategory.getCategory().getId())
                .name(subcategory.getCategory().getName())
                .build();
        return RespSubcategory.builder()
                .id(subcategory.getId())
                .name(subcategory.getName())
                .respCategory(respCategory)
                .build();
    }
}
