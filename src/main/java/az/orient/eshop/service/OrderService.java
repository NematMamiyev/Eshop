package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespOrder;
import az.orient.eshop.dto.response.Response;
import java.util.List;

public interface OrderService {
    Response<List<RespOrder>>  getList(Long customerId);
}
