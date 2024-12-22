package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqWarehouse;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.RespWarehouse;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface WarehouseService {
    Response<RespWarehouse> addWarehouse(ReqWarehouse reqWarehouse);

    Response<List<RespWarehouse>> getWarehouseList();

    Response<RespWarehouse> getWarehouseById(Long id);

    Response<RespWarehouse> updateWarehouse(Long id, ReqWarehouse reqWarehouse);

    RespStatus deleteWarehouse(Long id);
}
