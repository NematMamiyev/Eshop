package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqColor;
import az.orient.eshop.dto.response.RespColor;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Color;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.ColorRepository;
import az.orient.eshop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public Response<RespColor> addColor(ReqColor reqColor) {
        Response<RespColor> response = new Response<>();
        try {
            String name = reqColor.getName();
            if (name == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "name is invalid");
            }
            boolean uniqueName = colorRepository.existsColorByNameAndActive(name,EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            Color color = Color.builder()
                    .name(name)
                    .build();
            colorRepository.save(color);
            RespColor respColor = convert(color);
            response.setT(respColor);
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
    public Response<List<RespColor>> colorList() {
        Response<List<RespColor>> response = new Response<>();
        try {
            List<Color> colorList = colorRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (colorList.isEmpty()){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            List<RespColor> respColorList = colorList.stream().map(this::convert).toList();
            response.setT(respColorList);
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
    public Response<RespColor> getColorById(Long id) {
        Response<RespColor> response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
            }
            Color color = colorRepository.findByIdAndActive(id ,EnumAvailableStatus.ACTIVE.getValue());
            if (color == null){
                throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
            }
            RespColor respColor = convert(color);
            response.setT(respColor);
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
    public Response<RespColor> updateColor(ReqColor reqColor) {
        Response<RespColor> response = new Response<>();
        try {
            Long id = reqColor.getId();
            String name = reqColor.getName();
            if (id == null || name == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id or name is invalid");
            }
            boolean uniqueName = colorRepository.existsColorByNameAndActiveAndIdNot(name,EnumAvailableStatus.ACTIVE.getValue(), id);
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            Color color = colorRepository.findByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            color.setName(name);
            colorRepository.save(color);
            RespColor respColor = convert(color);
            response.setT(respColor);
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
    public Response deleteColor(Long id) {
        Response response = new Response<>();
        try {
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
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    private RespColor convert(Color color) {
        return RespColor.builder()
                .id(color.getId())
                .name(color.getName())
                .build();
    }
}
