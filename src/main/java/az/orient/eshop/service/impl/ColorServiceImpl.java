package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqColor;
import az.orient.eshop.dto.response.RespColor;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Color;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ColorMapper;
import az.orient.eshop.repository.ColorRepository;
import az.orient.eshop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ColorServiceImpl.class);

    @Override
    public Response<RespColor> addColor(ReqColor reqColor) {
        Response<RespColor> response = new Response<>();
            String name = reqColor.getName();
            if (name == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "name is invalid");
            }
            boolean uniqueName = colorRepository.existsColorByNameAndActive(name,EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            Color color = colorMapper.toColor(reqColor);
            colorRepository.save(color);
            RespColor respColor = colorMapper.toRespColor(color);
            response.setT(respColor);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<List<RespColor>> colorList() {
        Response<List<RespColor>> response = new Response<>();
            List<Color> colorList = colorRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (colorList.isEmpty()){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            List<RespColor> respColorList = colorMapper.toRespColorList(colorList);
            response.setT(respColorList);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespColor> getColorById(Long id) {
        Response<RespColor> response = new Response<>();
            LOGGER.info("getColorById request: {}", id);
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
            }
            Color color = colorRepository.findByIdAndActive(id ,EnumAvailableStatus.ACTIVE.getValue());
            if (color == null){
                throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
            }
            RespColor respColor = colorMapper.toRespColor(color);
            response.setT(respColor);
            response.setStatus(RespStatus.getSuccessMessage());
            LOGGER.info("getColorById response: {}", response);
        return response;
    }

    @Override
    public Response<RespColor> updateColor(Long id, ReqColor reqColor) {
        Response<RespColor> response = new Response<>();
            String name = reqColor.getName();
            if (id == null || name == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id or name is invalid");
            }
            boolean uniqueName = colorRepository.existsColorByNameAndActiveAndIdNot(name,EnumAvailableStatus.ACTIVE.getValue(), id);
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            Color color = colorRepository.findByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            colorMapper.updateColorFromReqColor(color,reqColor);
            colorRepository.save(color);
            RespColor respColor = colorMapper.toRespColor(color);
            response.setT(respColor);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response deleteColor(Long id) {
        Response response = new Response<>();
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is invalid");
            }
            Color color = colorRepository.findByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (color == null){
                throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
            }
            color.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            colorRepository.save(color);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }
}
