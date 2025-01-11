package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespOrder;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping()
    public Response<List<RespOrder>>  getList(HttpServletRequest httpServletRequest){
        return orderService.getList(httpServletRequest);
    }
}
