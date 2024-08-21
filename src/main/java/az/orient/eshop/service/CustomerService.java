package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqCustomer;
import az.orient.eshop.dto.response.RespCustomer;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface CustomerService {
    Response<RespCustomer> addCustomer(ReqCustomer reqCustomer);

    Response<List<RespCustomer>> getCustomerList();

    Response<RespCustomer> getCustomerById(Long id);

    Response<RespCustomer> updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(Long id);
}
