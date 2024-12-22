package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqCart;
import az.orient.eshop.dto.response.RespCart;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Cart;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ProductDetailsMapper;
import az.orient.eshop.repository.CartRepository;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductDetailsMapper productDetailsMapper;

    @Override
    public RespStatus addCart(ReqCart reqCart) {
        ProductDetails productDetails = getProductDetails(reqCart.getProductDetailsId());
        Cart cart = getCart(reqCart.getCustomerId());
        cart.getProductDetailsList().add(productDetails);
        cart.setAmount(cart.getAmount().add(productDetails.getPrice()));
        cartRepository.save(cart);
        return RespStatus.getSuccessMessage();
    }

    @Override
    public RespStatus deleteCart(ReqCart reqCart) {
        ProductDetails productDetails = getProductDetails(reqCart.getProductDetailsId());
        Cart cart = getCart(reqCart.getCustomerId());
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
    public Response<RespCart> listByCustomerId(Long customerId) {
        Response<RespCart> response = new Response<>();
        Cart cart = getCart(customerId);
        List<RespProductDetails> respProductDetailsList = productDetailsMapper.toRespProductDetailsList(cart.getProductDetailsList());
        RespCart respCart = RespCart.builder()
                .respProductDetailsList(respProductDetailsList)
                .amount(cart.getAmount())
                .build();
        response.setT(respCart);
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
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
