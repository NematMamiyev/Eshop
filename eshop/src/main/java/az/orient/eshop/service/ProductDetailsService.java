package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.Response;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductDetailsService {
    Response<RespProductDetails> addProductDetails(Long productId, ReqProductDetails reqProductDetails);

    Response<RespProductDetails> updateProductDetails(Long id, ReqProductDetails reqProductDetails);

    Response<List<RespProductDetails>> getProductDetails();

    Response<RespProductDetails> getProductDetailsById(Long id);

    Response deleteProductDetails(Long id);
}
