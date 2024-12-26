package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.enums.PaymentMethod;
import az.orient.eshop.service.PaymentService;
import az.orient.eshop.validation.ValidPaymentMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("{paymentMethod}")
    public RespStatus payment(@PathVariable @NotNull(message = "Payment method is required")
                              @ValidPaymentMethod PaymentMethod paymentMethod, HttpServletRequest httpServletRequest) {
        return paymentService.payment(paymentMethod, httpServletRequest);
    }
}
