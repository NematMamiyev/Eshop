package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqOrderStatus;
import az.orient.eshop.dto.response.RespOrderStatus;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface OrderStatusService {

    Response<RespOrderStatus> addOrderStatus(ReqOrderStatus reqOrderStatus);

    Response<List<RespOrderStatus>> orderStatusList();

    Response<RespOrderStatus> getOrderStatusById(Long id);

    Response<RespOrderStatus> updateOrderStatus(ReqOrderStatus reqOrderStatus);

    Response deleteOrderStatus(Long id);
}
