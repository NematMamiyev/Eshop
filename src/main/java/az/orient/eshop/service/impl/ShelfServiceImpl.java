package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqShelf;
import az.orient.eshop.dto.response.RespShelf;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Shelf;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ShelfMapper;
import az.orient.eshop.repository.ShelfRepository;
import az.orient.eshop.repository.WarehouseRepository;
import az.orient.eshop.service.ShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelfServiceImpl implements ShelfService {
    private final ShelfRepository shelfRepository;
    private final ShelfMapper shelfMapper;

    @Override
    public Response<RespShelf> addShelf(ReqShelf reqShelf) {
        Response<RespShelf> response = new Response<>();
        if (shelfRepository.existsShelfByNameAndActive(reqShelf.getName(), EnumAvailableStatus.ACTIVE.getValue())) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
        }
        Shelf shelf = shelfRepository.save(shelfMapper.toShelf(reqShelf));
        response.setT(shelfMapper.toRespShelf(shelf));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<List<RespShelf>> shelfList() {
        Response<List<RespShelf>> response = new Response<>();
        List<Shelf> shelfList = shelfRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
        if (shelfList.isEmpty()) {
            throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
        }
        response.setT(shelfMapper.toRespShelfList(shelfList));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespShelf> getShelfById(Long id) {
        Response<RespShelf> response = new Response<>();
        Shelf shelf = getShelf(id);
        response.setT(shelfMapper.toRespShelf(shelf));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespShelf> updateShelf(Long id, ReqShelf reqShelf) {
        Response<RespShelf> response = new Response<>();
        if (shelfRepository.existsShelfByNameAndActiveAndIdNot(reqShelf.getName(), EnumAvailableStatus.ACTIVE.getValue(), id)) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
        }
        Shelf shelf = getShelf(id);
        shelfMapper.updateShelfFromReqShelf(shelf, reqShelf);
        shelfRepository.save(shelf);
        response.setT(shelfMapper.toRespShelf(shelf));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public RespStatus deleteShelf(Long id) {
        Shelf shelf = getShelf(id);
        shelf.setActive(EnumAvailableStatus.DEACTIVATED.getValue());
        shelfRepository.save(shelf);
        return null;
    }

    private Shelf getShelf(Long id) {
        Shelf shelf = shelfRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
        if (shelf == null) {
            throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
        }
        return shelf;
    }
}
