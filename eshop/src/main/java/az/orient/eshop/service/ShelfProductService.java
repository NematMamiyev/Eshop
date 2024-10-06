package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqProduct;
import az.orient.eshop.dto.request.ReqShelf;
import az.orient.eshop.dto.request.ReqShelfProduct;
import az.orient.eshop.dto.response.RespShelfProduct;
import az.orient.eshop.dto.response.Response;

public interface ShelfProductService {
    Response<RespShelfProduct> addProductInShelf(ReqShelfProduct reqShelfProduct);

    Response<RespShelfProduct> deleteProductInShelf(ReqShelfProduct reqShelfProduct);
}
