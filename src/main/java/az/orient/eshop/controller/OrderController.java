package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespOrder;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/list/{customerId}")
    public Response<List<RespOrder>>  getList(@PathVariable Long customerId){
        return orderService.getList(customerId);
    }
}
