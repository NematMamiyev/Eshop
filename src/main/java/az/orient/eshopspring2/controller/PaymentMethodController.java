package az.orient.eshopspring2.controller;

import az.orient.eshopspring2.dto.request.ReqPaymentMethod;
import az.orient.eshopspring2.dto.response.RespPaymentMethod;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paymentmethod")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @PostMapping("/create")
    public Response<RespPaymentMethod> addPaymentMethod(@RequestBody ReqPaymentMethod reqPaymentMethod){
        return paymentMethodService.addPaymentMethod(reqPaymentMethod);
    }

    @GetMapping("/list")
    public Response<List<RespPaymentMethod>> getPaymentMethodList(){
        return paymentMethodService.getPaymentMethodList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespPaymentMethod> getPaymentMethodById(@PathVariable Long id){
        return paymentMethodService.getPaymentMethodById(id);
    }

    @PutMapping("/update")
    public Response<RespPaymentMethod> updatePaymentMethod(@RequestBody ReqPaymentMethod reqPaymentMethod){
        return paymentMethodService.updatePaymentMethod(reqPaymentMethod);
    }

    @PutMapping("/delete/{id}")
    public Response deletePaymentMethod(@PathVariable Long id){
        return paymentMethodService.deletePaymentMethod(id);
    }
}
