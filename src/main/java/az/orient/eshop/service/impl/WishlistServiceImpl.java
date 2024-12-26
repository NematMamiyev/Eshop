package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.entity.Wishlist;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ProductDetailsMapper;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.WishlistRepository;
import az.orient.eshop.security.JwtGenerator;
import az.orient.eshop.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductDetailsMapper productDetailsMapper;
    private final JwtGenerator jwtGenerator;

    @Override
    public RespStatus addWishlist(Long productDetailsId, HttpServletRequest httpServletRequest) {
        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        Wishlist wishlist = wishlistRepository.findWishlistByCustomerIdAndActive(getCustomerId(httpServletRequest), EnumAvailableStatus.ACTIVE.getValue());
        if (wishlist == null) {
            throw new EshopException(ExceptionConstants.WISHLIST_NOT_FOUND, "Wishlist not found");
        }
        List<ProductDetails> productDetailsList = wishlist.getProductDetailsList();
        productDetailsList.add(productDetails);
        wishlist.setProductDetailsList(productDetailsList);
        wishlistRepository.save(wishlist);
        return RespStatus.getSuccessMessage();
    }

    @Override
    public RespStatus deleteWishlist(Long productDetailsId, HttpServletRequest httpServletRequest) {
        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        Wishlist wishlist = wishlistRepository.findWishlistByCustomerIdAndActive(getCustomerId(httpServletRequest), EnumAvailableStatus.ACTIVE.getValue());
        if (wishlist == null) {
            throw new EshopException(ExceptionConstants.WISHLIST_NOT_FOUND, "Wishlist not found");
        }
        List<ProductDetails> productDetailsList = wishlist.getProductDetailsList();
        productDetailsList.removeIf(product1 -> product1.equals(productDetails));
        wishlist.setProductDetailsList(productDetailsList);
        wishlistRepository.save(wishlist);
        return RespStatus.getSuccessMessage();
    }

    @Override
    public Response<List<RespProductDetails>> listByCustomerId(HttpServletRequest httpServletRequest) {
        Response<List<RespProductDetails>> response = new Response<>();
        Wishlist wishlist = wishlistRepository.findWishlistByCustomerIdAndActive(getCustomerId(httpServletRequest), EnumAvailableStatus.ACTIVE.getValue());
        if (wishlist == null) {
            throw new EshopException(ExceptionConstants.WISHLIST_NOT_FOUND, "Wishlist not found");
        }
        if (wishlist.getProductDetailsList().isEmpty()){
            throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND,"Wishlist is empty");
        }
        response.setT(productDetailsMapper.toRespProductDetailsList(wishlist.getProductDetailsList()));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    private Long getCustomerId(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtGenerator.getId(token);
        }
        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Token not found");
    }
}
