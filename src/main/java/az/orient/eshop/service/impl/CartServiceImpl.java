package az.orient.eshop.service.impl;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.Cart;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ProductDetailsMapper;
import az.orient.eshop.repository.CartRepository;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.security.JwtGenerator;
import az.orient.eshop.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductDetailsMapper productDetailsMapper;
    private final JwtGenerator jwtGenerator;

    @Override
    public RespStatus addCart(Long productDetailsId, HttpServletRequest httpServletRequest) {
        ProductDetails productDetails = getProductDetails(productDetailsId);
        Cart cart = getCart(getCustomerId(httpServletRequest));
        cart.getProductDetailsList().add(productDetails);
        cart.setAmount(cart.getAmount().add(productDetails.getPrice()));
        cartRepository.save(cart);
        return RespStatus.getSuccessMessage();
    }

    @Override
    public RespStatus deleteCart(Long productDetailsId, HttpServletRequest httpServletRequest) {
        ProductDetails productDetails = getProductDetails(productDetailsId);
        Cart cart = getCart(getCustomerId(httpServletRequest));
        List<ProductDetails> productDetailsList = cart.getProductDetailsList();
        if (productDetailsList.isEmpty()) {
            throw new EshopException(ExceptionConstants.PRODUCT_IS_NOT_IN_CART, "The product is not in the cart");
        }
        productDetailsList.removeIf(product1 -> product1.equals(productDetails));
        cart.setAmount(cart.getAmount().subtract(productDetails.getPrice()));
        cart.setProductDetailsList(productDetailsList);
        cartRepository.save(cart);
        return RespStatus.getSuccessMessage();
    }

    @Override
    public Response<RespCart> listByCustomerId(HttpServletRequest httpServletRequest) {
        Response<RespCart> response = new Response<>();
        Cart cart = getCart(getCustomerId(httpServletRequest));
        if (cart.getProductDetailsList().isEmpty()){
            throw new EshopException(ExceptionConstants.CART_IS_EMPTY, "Cart is empty");
        }
        List<RespProductDetails> respProductDetailsList = productDetailsMapper.toRespProductDetailsList(cart.getProductDetailsList());
        RespCart respCart = RespCart.builder()
                .respProductDetailsList(respProductDetailsList)
                .amount(cart.getAmount())
                .build();
        response.setT(respCart);
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

    private Cart getCart(Long customerId) {
        Cart cart = cartRepository.findCartByCustomerIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
        if (cart == null) {
            throw new EshopException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
        }
        return cart;
    }

    private ProductDetails getProductDetails(Long productDetailsId) {
        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        if (productDetails == null) {
            throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
        }
        return productDetails;
    }
}
