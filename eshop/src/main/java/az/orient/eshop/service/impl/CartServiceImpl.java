package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqCart;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ProductDetailsMapper;
import az.orient.eshop.repository.CartRepository;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.service.CartService;
import az.orient.eshop.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final CustomerRepository customerRepository;
    private final ProductDetailsMapper productDetailsMapper;

    @Override
    public Response addCart(ReqCart reqCart) {
        Response response = new Response<>();
        try {
            Long productDetailsId = reqCart.getProductDetailsId();
            Long customerId = reqCart.getCustomerId();
            if (productDetailsId == null || customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product id or customer id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Cart cart = cartRepository.findCartByCustomerIdAndActive(customerId,EnumAvailableStatus.ACTIVE.getValue());
            if(cart == null){
                throw new EshopException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
            }
            List<ProductDetails> productDetailsList = cart.getProductDetailsList();
            productDetailsList.add(productDetails);
            BigDecimal amount = BigDecimal.ZERO;
            for (ProductDetails product1 : productDetailsList){
                amount = amount.add(product1.getPrice());
            }
            cart.setAmount(amount );
            cartRepository.save(cart);
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
    public Response deleteCart(ReqCart reqCart) {
        Response response = new Response<>();
        try {
            Long productDetailsId = reqCart.getProductDetailsId();
            Long customerId = reqCart.getCustomerId();
            if (productDetailsId == null || customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product id or customer id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Cart cart = cartRepository.findCartByCustomerIdAndActive(customerId,EnumAvailableStatus.ACTIVE.getValue());
            if(cart == null){
                throw new EshopException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
            }
            List<ProductDetails> productDetailsList = cart.getProductDetailsList();
            if (productDetailsList.isEmpty()){
                throw new EshopException(ExceptionConstants.PRODUCT_IS_NOT_IN_CART,"The product is not in the cart");
            }
            BigDecimal amount = cart.getAmount();
            amount = amount.subtract(productDetails.getPrice());
            productDetailsList.removeIf(product1 -> product1.equals(productDetails));
            cart.setAmount(amount);
            cart.setProductDetailsList(productDetailsList);
            cartRepository.save(cart);
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
    public Response<RespCart> listByCustomerId(Long customerId) {
        Response<RespCart> response = new Response<>();
        try {
            if (customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Customer id is null");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Cart cart = cartRepository.findCartByCustomerIdAndActive(customerId,EnumAvailableStatus.ACTIVE.getValue());
            if(cart == null){
                throw new EshopException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
            }
            List<ProductDetails> productDetailsList = cart.getProductDetailsList();
            List<RespProductDetails> respProductDetailsList = productDetailsMapper.toRespProductDetailsList(productDetailsList);
            RespCart respCart = RespCart.builder()
                    .respProductDetailsList(respProductDetailsList)
                    .amount(cart.getAmount())
                    .build();
            response.setT(respCart);
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
