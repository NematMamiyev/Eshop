package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqShelf;
import az.orient.eshopspring2.dto.response.RespShelf;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface ShelfService {
    Response<RespShelf> addShelf(ReqShelf reqShelf);

    Response<List<RespShelf>> shelfList();

    Response<RespShelf> getShelfById(Long id);

    Response<RespShelf> updateShelf(ReqShelf reqShelf);

    Response deleteShelf(Long id);
}
