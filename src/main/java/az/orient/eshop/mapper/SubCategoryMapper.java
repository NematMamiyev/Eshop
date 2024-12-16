package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqSubcategory;
import az.orient.eshop.dto.response.RespSubcategory;
import az.orient.eshop.entity.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MapperHelper.class})
public interface SubCategoryMapper {
    @Mapping(source = "categoryId", target = "category", qualifiedByName = "mapCategory")
    Subcategory toSubcategory(ReqSubcategory reqSubcategory);

    @Mapping(source = "category", target = "respCategory")
    RespSubcategory toRespSubcategory(Subcategory subcategory);

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "mapCategory")
    void updateSubcategoryFromReqSubcategory(@MappingTarget Subcategory subcategory, ReqSubcategory reqSubcategory);

    List<RespSubcategory> toRespSubcategoryList(List<Subcategory> subcategoryList);
}