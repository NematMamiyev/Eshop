package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqCategory;
import az.orient.eshop.dto.response.RespCategory;
import az.orient.eshop.entity.Category;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.repository.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(ReqCategory reqCategory);
    RespCategory toRespCategory(Category category);
    List<RespCategory> toRespCategoryList(List<Category> categoryList);
    void updateCategoryFromReqCategory(@MappingTarget Category category,ReqCategory reqCategory);

}
