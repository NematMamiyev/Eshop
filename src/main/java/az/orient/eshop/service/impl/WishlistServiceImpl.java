package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqWishlist;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.MapperHelper;
import az.orient.eshop.mapper.ProductDetailsMapper;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.WishlistRepository;
import az.orient.eshop.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final CustomerRepository customerRepository;
    private final ProductDetailsMapper productDetailsMapper;

    @Override
    public Response addWishlist(ReqWishlist reqWishlist) {
        Response response = new Response<>();
        try {
            Long productDetailsId = reqWishlist.getProductDetailsId();
            Long customerId = reqWishlist.getCustomerId();
            if (productDetailsId == null || customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id or customer id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Wishlist wishlist = wishlistRepository.findWishlistByCustomerIdAndActive(customerId,EnumAvailableStatus.ACTIVE.getValue());
            if (wishlist == null) {
                throw new EshopException(ExceptionConstants.WISHLIST_NOT_FOUND,"Wishlist not found");
            }
            List<ProductDetails> productDetailsList = wishlist.getProductDetailsList();
            productDetailsList.add(productDetails);
            wishlist.setProductDetailsList(productDetailsList);
            wishlistRepository.save(wishlist);
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
    public Response deleteWishlist(ReqWishlist reqWishlist) {
        Response response = new Response<>();
        try {
            Long productDetailsId = reqWishlist.getProductDetailsId();
            Long customerId = reqWishlist.getCustomerId();
            if (productDetailsId == null || customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id or customer id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Wishlist wishlist = wishlistRepository.findWishlistByCustomerIdAndActive(customerId,EnumAvailableStatus.ACTIVE.getValue());
            if (wishlist == null){
                throw new EshopException(ExceptionConstants.WISHLIST_NOT_FOUND, "Wishlist not found");
            }
            List<ProductDetails> productDetailsList = wishlist.getProductDetailsList();
            productDetailsList.removeIf(product1 -> product1.equals(productDetails));
            wishlist.setProductDetailsList(productDetailsList);
            wishlistRepository.save(wishlist);
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
    public Response<List<RespProductDetails>> listByCustomerId(Long customerId) {
        Response<List<RespProductDetails>> response = new Response<>();
        try {
            if (customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Customer id is null");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Wishlist wishlist = wishlistRepository.findWishlistByCustomerIdAndActive(customerId,EnumAvailableStatus.ACTIVE.getValue());
            if (wishlist == null){
                throw new EshopException(ExceptionConstants.WISHLIST_NOT_FOUND, "Wishlist not found");
            }
            response.setT(productDetailsMapper.toRespProductDetailsList(wishlist.getProductDetailsList()));
            response.setStatus(RespStatus.getSuccessMessage());
        }  catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }


}
