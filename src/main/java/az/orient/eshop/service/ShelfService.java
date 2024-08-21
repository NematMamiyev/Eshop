package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqShelf;
import az.orient.eshop.dto.response.RespShelf;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface ShelfService {
    Response<RespShelf> addShelf(ReqShelf reqShelf);

    Response<List<RespShelf>> shelfList();

    Response<RespShelf> getShelfById(Long id);

    Response<RespShelf> updateShelf(ReqShelf reqShelf);

    Response deleteShelf(Long id);
}
