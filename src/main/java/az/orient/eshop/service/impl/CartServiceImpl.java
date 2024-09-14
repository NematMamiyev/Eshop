package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqCart;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.CartRepository;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.ProductRepository;
import az.orient.eshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final CustomerRepository customerRepository;

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
            Float amount = 0F;
            for (ProductDetails product1 : productDetailsList){
                amount+=product1.getPrice();
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
            Float amount = cart.getAmount();
            amount-=productDetails.getPrice();
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
            List<RespProductDetails> respProductDetailsList = productDetailsList.stream().map(this::convertToRespProductDetails).toList();
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
    private RespProductImage convertToRespProductImage(ProductImage productImage) {
        return RespProductImage.builder()
                .data(productImage.getData())
                .fileName(productImage.getFileName())
                .fileType(productImage.getFileType())
                .build();
    }

    private RespProductVideo convertToRespProductVideo(ProductVideo productVideo) {
        return RespProductVideo.builder()
                .data(productVideo.getData())
                .fileName(productVideo.getFileName())
                .fileType(productVideo.getFileType())
                .build();
    }
    private RespProductDetails convertToRespProductDetails(ProductDetails productDetails) {
        Set<RespProductImage> respProductImages = Optional.ofNullable(productDetails.getImages())
                .orElse(Collections.emptySet())
                .stream()
                .map(this::convertToRespProductImage)
                .collect(Collectors.toSet());
        Set<RespProductVideo> respProductVideos = Optional.ofNullable(productDetails.getVideos())
                .orElse(Collections.emptySet())
                .stream()
                .map(this::convertToRespProductVideo)
                .collect(Collectors.toSet()); RespSize respSize = RespSize.builder()
                .id(productDetails.getSize().getId())
                .name(productDetails.getSize().getName())
                .build();
        RespColor respColor = RespColor.builder()
                .id(productDetails.getColor().getId())
                .name(productDetails.getColor().getName())
                .build();
        return RespProductDetails.builder()
                .id(productDetails.getId())
                .respSize(respSize)
                .respColor(respColor)
                .currency(productDetails.getCurrency())
                .price(productDetails.getPrice())
                .stock(productDetails.getStock())
                .respProductVideoList(respProductVideos)
                .respProductImageList(respProductImages)
                .build();
    }
}
