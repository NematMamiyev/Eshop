package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespOrderStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.OrderStatusService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orderstatus")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/{orderId}")
    public Response<List<RespOrderStatus>> getOrderStatusList(@PathVariable @NotNull(message = "Id is required") Long orderId){
        return orderStatusService.getOrderStatusList(orderId);
    }
}
