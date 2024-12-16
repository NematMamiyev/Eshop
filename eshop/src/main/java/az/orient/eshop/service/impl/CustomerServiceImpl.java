package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqCustomer;
import az.orient.eshop.dto.response.RespCustomer;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Cart;
import az.orient.eshop.entity.Customer;
import az.orient.eshop.entity.Wishlist;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Gender;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.CustomerMapper;
import az.orient.eshop.repository.CartRepository;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.WishlistRepository;
import az.orient.eshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Response<RespCustomer> addCustomer(ReqCustomer reqCustomer) {
        Response<RespCustomer> response = new Response<>();
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            String email = reqCustomer.getEmail();
            String password = reqCustomer.getPassword();
            String phone = reqCustomer.getPhone();
            Gender gender = reqCustomer.getGender();
            if (name == null || surname == null || email == null || password == null || phone == null || gender == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            boolean uniqueEmail = customerRepository.existsCustomerByEmailIgnoreCaseAndActive(email, EnumAvailableStatus.ACTIVE.getValue());
            boolean uniquePhone = customerRepository.existsCustomerByPhoneIgnoreCaseAndActive(phone, EnumAvailableStatus.ACTIVE.getValue());
            if (uniquePhone || uniqueEmail) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Email or phone number available in the database");
            }
            Customer customer = customerMapper.toCustomer(reqCustomer);
            customerRepository.save(customer);
            Cart cart = Cart.builder()
                    .customer(customer)
                    .build();
            cartRepository.save(cart);
            Wishlist wishlist = Wishlist.builder()
                    .customer(customer)
                    .build();
            wishlistRepository.save(wishlist);
            customer.setCart(cart);
            customer.setWishlist(wishlist);
            customerRepository.save(customer);
            RespCustomer respCustomer = customerMapper.toRespCustomer(customer);
            response.setT(respCustomer);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<List<RespCustomer>> getCustomerList() {
        Response<List<RespCustomer>> response = new Response<>();
        try {
            List<Customer> customerList = customerRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (customerList.isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Customer list empty");
            }
            List<RespCustomer> respCustomerList =customerMapper.toRespCustomerList(customerList);
            response.setT(respCustomerList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespCustomer> getCustomerById(Long id) {
        Response<RespCustomer> response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            RespCustomer respCustomer = customerMapper.toRespCustomer(customer);
            response.setT(respCustomer);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespCustomer> updateCustomer(Long id, ReqCustomer reqCustomer) {
        Response<RespCustomer> response = new Response<>();
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            String email = reqCustomer.getEmail();
            String password = reqCustomer.getPassword();
            String phone = reqCustomer.getPhone();
            Gender gender = reqCustomer.getGender();
            if (id == null || name == null || surname == null || email == null || password == null || phone == null || gender == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            boolean uniqueEmail = customerRepository.existsCustomerByEmailIgnoreCaseAndActiveAndIdNot(email, EnumAvailableStatus.ACTIVE.getValue(), id);
            boolean uniquePhone = customerRepository.existsCustomerByPhoneIgnoreCaseAndActiveAndIdNot(phone, EnumAvailableStatus.ACTIVE.getValue(), id);
            if (uniquePhone || uniqueEmail) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Email or phone number available in the database");
            }
            customerMapper.updateCustomerFromReqCustomer(customer,reqCustomer);
            customerRepository.save(customer);
            RespCustomer respCustomer = customerMapper.toRespCustomer(customer);
            response.setT(respCustomer);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response deleteCustomer(Long id) {
        Response response = new Response<>();
        try {
            if (id ==  null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Id not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null){
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            customer.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            customerRepository.save(customer);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }
}
