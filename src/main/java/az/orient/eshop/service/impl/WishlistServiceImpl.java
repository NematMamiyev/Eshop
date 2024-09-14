package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqWishlist;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.WishlistRepository;
import az.orient.eshop.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final CustomerRepository customerRepository;

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
    public Response<RespWishlist> listByCustomerId(Long customerId) {
        Response<RespWishlist> response = new Response<>();
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
            List<ProductDetails> productDetailsList = wishlist.getProductDetailsList();
            List<RespProductDetails> respProductDetailsList = productDetailsList.stream().map(this::convertToRespProductDetails).toList();
            RespWishlist respWishlist = RespWishlist.builder()
                    .respProductDetailsList(respProductDetailsList)
                    .build();
            response.setT(respWishlist);
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
                .collect(Collectors.toSet());RespSize respSize = RespSize.builder()
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
