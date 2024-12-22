package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqProduct;
import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.RespProduct;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface ProductService {
    Response<RespProduct> addProduct(ReqProduct reqProduct, List<ReqProductDetails> reqProductDetailsList);

    Response<List<RespProduct>> getProductList();

    Response<RespProduct> getProductById(Long id);

    Response<RespProduct> updateProduct(Long id, ReqProduct reqProduct);

    RespStatus deleteProduct(Long id);
}
