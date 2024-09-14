package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespWareHouseWork;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface WarehouseWorkService {
    Response<List<RespWareHouseWork>> works();

    Response<RespWareHouseWork> handleWork(Long id);
}
