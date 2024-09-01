package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.Cart;
import az.orient.eshop.entity.Customer;
import az.orient.eshop.entity.Product;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.CartRepository;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.ProductRepository;
import az.orient.eshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Response<List<RespCart>> getCartList() {
        Response<List<RespCart>> response = new Response<>();
        try {
            List<Cart> cartList = cartRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (cartList.isEmpty()) {
                throw new EshopException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
            }
            List<RespCart> respCartList = cartList.stream().map(this::convert).toList();
            response.setT(respCartList);
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
    public Response addCart(Long productId, Long customerId) {
        Response response = new Response<>();
        try {
            if (productId == null || customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product id or customer id is null");
            }
            Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Cart cart = Cart.builder()
                    .product(product)
                    .customer(customer)
                    .build();
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
    public Response deleteCart(Long productId, Long customerId) {
        Response response = new Response<>();
        try {
            if (productId == null || customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product id or customer id is null");
            }
            Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Cart cart = cartRepository.findCartByProductIdAndCustomerIdAndActive(productId, customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (cart == null) {
                throw new EshopException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
            }
            cart.setActive(EnumAvailableStatus.DEACTIVE.getValue());
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
    public Response<List<RespCart>> listByCustomerId(Long customerId) {
        Response<List<RespCart>> response = new Response<>();
        try {
            if (customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Customer id is null");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<Cart> cartList = cartRepository.findCartByCustomerIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (cartList.isEmpty()) {
                throw new EshopException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
            }
            List<RespCart> respCart = cartList.stream().map(this::convert).toList();
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

    private RespCart convert(Cart cart) {
        RespColor respColor = RespColor.builder()
                .id(cart.getProduct().getColor().getId())
                .name(cart.getProduct().getColor().getName())
                .build();
        RespBrand respBrand = RespBrand.builder()
                .id(cart.getProduct().getBrand().getId())
                .name(cart.getProduct().getBrand().getName())
                .build();
        RespSize respSize = RespSize.builder()
                .id(cart.getProduct().getSize().getId())
                .name(cart.getProduct().getSize().getName())
                .build();
        RespProduct respProduct = RespProduct.builder()
                .id(cart.getProduct().getId())
                .name(cart.getProduct().getName())
                .image(cart.getProduct().getImage())
                .video(cart.getProduct().getVideo())
                .respColor(respColor)
                .respBrand(respBrand)
                .gender(cart.getProduct().getGender())
                .respSize(respSize)
                .price(cart.getProduct().getPrice())
                .build();
        return RespCart.builder()
                .respProduct(respProduct)
                .build();
    }

}
