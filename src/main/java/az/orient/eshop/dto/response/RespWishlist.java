package az.orient.eshop.dto.response;

import az.orient.eshop.entity.Customer;
import az.orient.eshop.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespWishlist {
    private Long id;
    private RespProduct respProduct;
    private RespCustomer respCustomer;
}
