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
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public Response<RespCustomer> addCustomer(@RequestBody ReqCustomer reqCustomer){
        return customerService.addCustomer(reqCustomer);
    }

    @GetMapping("/list")
    public Response<List<RespCustomer>> getCustomerList(){
        return customerService.getCustomerList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespCustomer> getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PutMapping("/update")
    public Response<RespCustomer> updateCustomer(@RequestBody ReqCustomer reqCustomer){
        return customerService.updateCustomer(reqCustomer);
    }

    @PutMapping("/delete/{id}")
    public Response deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomer(id);
    }
}