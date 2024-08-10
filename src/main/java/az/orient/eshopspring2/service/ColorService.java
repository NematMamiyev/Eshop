package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqColor;
import az.orient.eshopspring2.dto.response.RespColor;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface ColorService {

    Response<RespColor> addColor(ReqColor reqColor);

    Response<List<RespColor>> colorList();

    Response<RespColor> getColorById(Long id);

    Response<RespColor> updateColor(ReqColor reqColor);

    Response deleteColor(Long id);
}
