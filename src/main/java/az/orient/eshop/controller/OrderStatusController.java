package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespOrderStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orderstatus")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    @GetMapping("/list/{orderId}")
    public Response<List<RespOrderStatus>> getOrderStatusList(@PathVariable Long orderId){
        return orderStatusService.getOrderStatusList(orderId);
    }
}
