package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqCustomer;
import az.orient.eshopspring2.dto.response.RespCustomer;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface CustomerService {
    Response<RespCustomer> addCustomer(ReqCustomer reqCustomer);

    Response<List<RespCustomer>> getCustomerList();

    Response<RespCustomer> getCustomerById(Long id);

    Response<RespCustomer> updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(Long id);
}
