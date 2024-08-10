package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqWarehouse;
import az.orient.eshopspring2.dto.response.RespWarehouse;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface WarehouseService {
    Response<RespWarehouse> addWarehouse(ReqWarehouse reqWarehouse);

    Response<List<RespWarehouse>> getWarehouseList();

    Response<RespWarehouse> getWarehouseById(Long id);

    Response<RespWarehouse> updateWarehouse(ReqWarehouse reqWarehouse);

    Response deleteWarehouse(Long id);
}
