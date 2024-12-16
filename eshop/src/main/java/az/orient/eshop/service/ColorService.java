package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqColor;
import az.orient.eshop.dto.response.RespColor;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface ColorService {

    Response<RespColor> addColor(ReqColor reqColor);

    Response<List<RespColor>> colorList();

    Response<RespColor> getColorById(Long id);

    Response<RespColor> updateColor(Long id, ReqColor reqColor);

    Response deleteColor(Long id);
}
