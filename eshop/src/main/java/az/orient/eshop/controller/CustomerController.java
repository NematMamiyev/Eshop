package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqCustomer;
import az.orient.eshop.dto.response.RespCustomer;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public Response<RespCustomer> addCustomer(@RequestBody ReqCustomer reqCustomer){
        return customerService.addCustomer(reqCustomer);
    }

    @GetMapping
    public Response<List<RespCustomer>> getCustomerList(){
        return customerService.getCustomerList();
    }

    @GetMapping("/{id}")
    public Response<RespCustomer> getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PutMapping
    public Response<RespCustomer> updateCustomer(@RequestBody ReqCustomer reqCustomer){
        return customerService.updateCustomer(reqCustomer);
    }

    @DeleteMapping("/{id}")
    public Response deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomer(id);
    }
}
