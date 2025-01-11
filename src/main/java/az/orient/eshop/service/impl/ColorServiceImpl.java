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
        boolean uniqueName = colorRepository.existsColorByNameAndActive(reqColor.getName(), EnumAvailableStatus.ACTIVE.getValue());
        if (uniqueName) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
        }
        Color color = colorMapper.toColor(reqColor);
        colorRepository.save(color);
        response.setT(colorMapper.toRespColor(color));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<List<RespColor>> colorList() {
        Response<List<RespColor>> response = new Response<>();
        List<Color> colorList = colorRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
        if (colorList.isEmpty()) {
            throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
        }
        response.setT(colorMapper.toRespColorList(colorList));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespColor> getColorById(Long id) {
        Response<RespColor> response = new Response<>();
        LOGGER.info("getColorById request: {}", id);
        Color color = getColor(id);
        response.setT(colorMapper.toRespColor(color));
        response.setStatus(RespStatus.getSuccessMessage());
        LOGGER.info("getColorById response: {}", response);
        return response;
    }

    @Override
    public Response<RespColor> updateColor(Long id, ReqColor reqColor) {
        Response<RespColor> response = new Response<>();
        boolean uniqueName = colorRepository.existsColorByNameAndActiveAndIdNot(reqColor.getName(), EnumAvailableStatus.ACTIVE.getValue(), id);
        if (uniqueName) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
        }
        Color color = getColor(id);
        colorMapper.updateColorFromReqColor(color, reqColor);
        colorRepository.save(color);
        response.setT(colorMapper.toRespColor(color));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public RespStatus deleteColor(Long id) {
        Color color = getColor(id);
        color.setActive(EnumAvailableStatus.DEACTIVATED.getValue());
        colorRepository.save(color);
        return RespStatus.getSuccessMessage();
    }

    private Color getColor(Long id) {
        Color color = colorRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
        if (color == null) {
            throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
        }
        return color;
    }
}
