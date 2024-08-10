package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqSize;
import az.orient.eshopspring2.dto.response.RespSize;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface SizeService {
    Response<RespSize> addSize(ReqSize reqSize);

    Response<List<RespSize>> getSizeList();

    Response<RespSize> getSizeById(Long id);

    Response<RespSize> updateSize(ReqSize reqSize);

    Response deleteSize(Long id);
}
