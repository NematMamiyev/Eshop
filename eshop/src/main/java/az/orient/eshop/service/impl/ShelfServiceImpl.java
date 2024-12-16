package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqShelf;
import az.orient.eshop.dto.response.RespShelf;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.RespWarehouse;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Shelf;
import az.orient.eshop.entity.Warehouse;
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
    private final WarehouseRepository warehouseRepository;
    private final ShelfMapper shelfMapper;

    @Override
    public Response<RespShelf> addShelf(ReqShelf reqShelf) {
        Response<RespShelf> response = new Response<>();
        try {
            String name = reqShelf.getName();
            Long warehouseId = reqShelf.getWarehouseId();
            if (name == null || warehouseId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Warehouse warehouse = warehouseRepository.findWarehouseByIdAndActive(warehouseId, EnumAvailableStatus.ACTIVE.getValue());
            if (warehouse == null) {
                throw new EshopException(ExceptionConstants.WAREHOUSE_NOT_FOUND, "Warehouse not found");
            }
            Shelf shelf = shelfMapper.toShelf(reqShelf);
            shelfRepository.save(shelfMapper.toShelf(reqShelf));
            RespShelf respShelf = shelfMapper.toRespShelf(shelf);
            response.setT(respShelf);
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
    public Response<List<RespShelf>> shelfList() {
        Response<List<RespShelf>> response = new Response<>();
        try {
            List<Shelf> shelfList = shelfRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (shelfList.isEmpty()) {
                throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
            }
            response.setT(shelfMapper.toRespShelfList(shelfList));
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
    public Response<RespShelf> getShelfById(Long id) {
        Response<RespShelf> response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Shelf shelf = shelfRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (shelf == null) {
                throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
            }
            response.setT(shelfMapper.toRespShelf(shelf));
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
    public Response<RespShelf> updateShelf(Long id, ReqShelf reqShelf) {
        Response<RespShelf> response = new Response<>();
        try {
            String name = reqShelf.getName();
            Long warehouseId = reqShelf.getWarehouseId();
            if (id == null || name == null || warehouseId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Shelf shelf = shelfRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (shelf == null) {
                throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
            }
            Warehouse warehouse = warehouseRepository.findWarehouseByIdAndActive(warehouseId, EnumAvailableStatus.ACTIVE.getValue());
            if (warehouse == null) {
                throw new EshopException(ExceptionConstants.WAREHOUSE_NOT_FOUND, "Warehouse not found");
            }
            shelfMapper.updateShelfFromReqShelf(shelf,reqShelf);
            shelfRepository.save(shelf);
            response.setT(shelfMapper.toRespShelf(shelf));
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
    public Response deleteShelf(Long id) {
        Response response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
            }
            Shelf shelf = shelfRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (shelf == null) {
                throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
            }
            shelf.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            shelfRepository.save(shelf);
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
}
