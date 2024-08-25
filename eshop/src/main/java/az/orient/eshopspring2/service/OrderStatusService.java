package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqOrderStatus;
import az.orient.eshopspring2.dto.response.RespOrderStatus;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface OrderStatusService {

    Response<RespOrderStatus> addOrderStatus(ReqOrderStatus reqOrderStatus);

    Response<List<RespOrderStatus>> orderStatusList();

    Response<RespOrderStatus> getOrderStatusById(Long id);

    Response<RespOrderStatus> updateOrderStatus(ReqOrderStatus reqOrderStatus);

    Response deleteOrderStatus(Long id);
}
