package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespOrderStatus;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface OrderStatusService {
    Response<List<RespOrderStatus>> getOrderStatusList(Long orderId);
}
