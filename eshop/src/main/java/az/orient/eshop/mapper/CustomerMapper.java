package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqCustomer;
import az.orient.eshop.dto.response.RespCustomer;
import az.orient.eshop.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(ReqCustomer reqCustomer);
    RespCustomer toRespCustomer(Customer customer);
    List<RespCustomer> toRespCustomerList(List<Customer> customerList);
    void updateCustomerFromReqCustomer(@MappingTarget Customer customer, ReqCustomer reqCustomer);
}
