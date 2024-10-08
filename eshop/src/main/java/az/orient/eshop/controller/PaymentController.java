package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqPayment;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("payment")
public class  PaymentController {

    private final PaymentService paymentService;
    @PostMapping
    public Response payment(@RequestBody ReqPayment reqPayment){
        return paymentService.payment(reqPayment);
    }
}
