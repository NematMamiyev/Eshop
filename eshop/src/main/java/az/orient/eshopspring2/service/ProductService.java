package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqProduct;
import az.orient.eshopspring2.dto.response.RespProduct;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface ProductService {
    Response<RespProduct> addProduct(ReqProduct reqProduct);

    Response<List<RespProduct>> getProductList();

    Response<RespProduct> getProductById(Long id);

    Response<RespProduct> updateProduct(ReqProduct reqProduct);

    Response deleteProduct(Long id);
}
