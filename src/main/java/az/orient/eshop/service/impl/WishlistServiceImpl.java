package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.Customer;
import az.orient.eshop.entity.Product;
import az.orient.eshop.entity.Wishlist;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.ProductRepository;
import az.orient.eshop.repository.WishlistRepository;
import az.orient.eshop.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    @Override
    public Response<List<RespWishlist>> getWishlistList() {
        Response<List<RespWishlist>> response = new Response<>();
        try {
            List<Wishlist> wishlistList = wishlistRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (wishlistList.isEmpty()) {
                throw new EshopException(ExceptionConstants.WISHLIST_NOT_FOUND, "Wishlist not found");
            }
            List<RespWishlist> respWishlistList = wishlistList.stream().map(this::convert).toList();
            response.setT(respWishlistList);
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
    public Response addWishlist(Long productId, Long customerId) {
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
            Wishlist wishlist = Wishlist.builder()
                    .product(product)
                    .customer(customer)
                    .build();
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
    public Response deleteWishlist(Long productId, Long customerId) {
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
            Wishlist wishlist = wishlistRepository.findWishlistByProductIdAndCustomerIdAndActive(productId,customerId,EnumAvailableStatus.ACTIVE.getValue());
            if (wishlist == null){
                throw new EshopException(ExceptionConstants.WISHLIST_NOT_FOUND, "Wishlist not found");
            }
            wishlist.setActive(EnumAvailableStatus.DEACTIVE.getValue());
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
    public Response<List<RespWishlist>> listByCustomerId(Long customerId) {
        Response<List<RespWishlist>> response = new Response<>();
        try {
            if (customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Customer id is null");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<Wishlist> wishlistList = wishlistRepository.findWishlistByCustomerIdAndActive(customerId,EnumAvailableStatus.ACTIVE.getValue());
            if (wishlistList.isEmpty()){
                throw new EshopException(ExceptionConstants.WISHLIST_NOT_FOUND, "Wishlist not found");
            }
            List<RespWishlist> respWishlist = wishlistList.stream().map(this::convert).toList();
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

    private RespWishlist convert(Wishlist wishlist) {
        RespColor respColor = RespColor.builder()
                .id(wishlist.getProduct().getColor().getId())
                .name(wishlist.getProduct().getColor().getName())
                .build();
        RespBrand respBrand = RespBrand.builder()
                .id(wishlist.getProduct().getBrand().getId())
                .name(wishlist.getProduct().getBrand().getName())
                .build();
        RespSize respSize = RespSize.builder()
                .id(wishlist.getProduct().getSize().getId())
                .name(wishlist.getProduct().getSize().getName())
                .build();
        RespProduct respProduct = RespProduct.builder()
                .id(wishlist.getProduct().getId())
                .name(wishlist.getProduct().getName())
                .image(wishlist.getProduct().getImage())
                .video(wishlist.getProduct().getVideo())
                .respColor(respColor)
                .respBrand(respBrand)
                .gender(wishlist.getProduct().getGender())
                .respSize(respSize)
                .price(wishlist.getProduct().getPrice())
                .build();
        return RespWishlist.builder()
                .respProduct(respProduct)
                .build();
    }
}
