package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqShelfProductDetails;
import az.orient.eshop.dto.response.RespShelfProductDetails;
import az.orient.eshop.dto.response.Response;

public interface ShelfProductDetailsService {
    Response<RespShelfProductDetails> addProductInShelf(ReqShelfProductDetails reqShelfProduct);

    Response deleteProductInShelf(ReqShelfProductDetails reqShelfProduct);
}
