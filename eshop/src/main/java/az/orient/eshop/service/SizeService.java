package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqSize;
import az.orient.eshop.dto.response.RespSize;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface SizeService {
    Response<RespSize> addSize(ReqSize reqSize);

    Response<List<RespSize>> getSizeList();

    Response<RespSize> getSizeById(Long id);

    Response<RespSize> updateSize(Long id, ReqSize reqSize);

    Response deleteSize(Long id);
}
