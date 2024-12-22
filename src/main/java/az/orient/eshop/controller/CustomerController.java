package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqCustomer;
import az.orient.eshop.dto.response.RespCustomer;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public Response<RespCustomer> addCustomer(@RequestBody @Valid ReqCustomer reqCustomer){
        return customerService.addCustomer(reqCustomer);
    }

    @GetMapping
    public Response<List<RespCustomer>> getCustomerList(){
        return customerService.getCustomerList();
    }

    @GetMapping("/{id}")
    public Response<RespCustomer> getCustomerById(@PathVariable @NotNull(message = "Id is required") Long id){
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public Response<RespCustomer> updateCustomer(@PathVariable @NotNull(message = "Id is required") Long id,@RequestBody @Valid ReqCustomer reqCustomer){
        return customerService.updateCustomer(id, reqCustomer);
    }

    @DeleteMapping("/{id}")
    public RespStatus deleteCustomer(@PathVariable @NotNull(message = "Id is required") Long id){
        return customerService.deleteCustomer(id);
    }
}
