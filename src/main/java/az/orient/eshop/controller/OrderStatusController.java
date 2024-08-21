package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqOrderStatus;
import az.orient.eshop.dto.response.RespOrderStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderstatus")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    @PostMapping("/create")
    public Response<RespOrderStatus> addOrderStatus(@RequestBody ReqOrderStatus reqOrderStatus){
        return orderStatusService.addOrderStatus(reqOrderStatus);
    }

    @GetMapping("/list")
    public Response<List<RespOrderStatus>> orderStatusList(){
        return orderStatusService.orderStatusList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespOrderStatus> getOrderStatusById(@PathVariable Long id){
        return orderStatusService.getOrderStatusById(id);
    }

    @PutMapping("/update")
    public Response<RespOrderStatus> updateOrderStatus(@RequestBody ReqOrderStatus reqOrderStatus){
        return orderStatusService.updateOrderStatus(reqOrderStatus);
    }

    @PutMapping("/delete/{id}")
    public Response deleteOrderStatus(@PathVariable Long id){
        return orderStatusService.deleteOrderStatus(id);
    }

}
