package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqBrand;
import az.orient.eshop.dto.response.RespBrand;
import az.orient.eshop.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(ReqBrand reqBrand);
    RespBrand toRespBrand(Brand brand);
    List<RespBrand> toRespBrandList (List<Brand> brandList);
    void updateBrandFromReqBrand(@MappingTarget Brand brand, ReqBrand reqBrand);

}
