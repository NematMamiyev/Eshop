package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqSize;
import az.orient.eshop.dto.response.RespSize;
import az.orient.eshop.entity.Size;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeMapper {

    Size toSize(ReqSize reqSize);
    RespSize toRespSize(Size size);
    List<RespSize> toRespSizeList(List<Size> sizeList);
    void updateSizeFromReqSize(@MappingTarget Size size, ReqSize reqSize);
}
