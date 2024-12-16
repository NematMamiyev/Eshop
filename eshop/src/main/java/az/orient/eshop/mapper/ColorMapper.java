package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqColor;
import az.orient.eshop.dto.response.RespColor;
import az.orient.eshop.entity.Color;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    Color toColor(ReqColor reqColor);
    RespColor toRespColor(Color color);
    List<RespColor> toRespColorList(List<Color> colorList);
    void updateColorFromReqColor(@MappingTarget Color color,ReqColor reqColor);
}
