package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqSubcategory;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.RespSubcategory;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Subcategory;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.SubCategoryMapper;
import az.orient.eshop.repository.SubcategoryRepository;
import az.orient.eshop.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    @Override
    public Response<RespSubcategory> addSubcategory(ReqSubcategory reqSubcategory) {
        Response<RespSubcategory> response = new Response<>();
        try {
            String name = reqSubcategory.getName();
            Long categoryId = reqSubcategory.getCategoryId();
            if (name == null || categoryId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            boolean uniqueName = subcategoryRepository.existsSubcategoryByNameAndActive(name, EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            Subcategory subcategory = subCategoryMapper.toSubcategory(reqSubcategory);
            subcategoryRepository.save(subcategory);
            RespSubcategory respSubcategory = subCategoryMapper.toRespSubcategory(subcategory);
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
            List<RespSubcategory> respSubcategoryList = subCategoryMapper.toRespSubcategoryList(subcategoryList);
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
            RespSubcategory respSubcategory = subCategoryMapper.toRespSubcategory(subcategory);
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
    public Response<RespSubcategory> updateSubcategory(Long id, ReqSubcategory reqSubcategory) {
        Response<RespSubcategory> response = new Response<>();
        try {
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
            subCategoryMapper.updateSubcategoryFromReqSubcategory(subcategory,reqSubcategory);
            subcategoryRepository.save(subcategory);
            RespSubcategory respSubcategory = subCategoryMapper.toRespSubcategory(subcategory);
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
}
