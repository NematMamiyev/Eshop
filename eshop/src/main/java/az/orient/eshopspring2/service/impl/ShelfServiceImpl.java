package az.orient.eshopspring2.service.impl;

import az.orient.eshopspring2.dto.request.ReqShelf;
import az.orient.eshopspring2.dto.response.RespShelf;
import az.orient.eshopspring2.dto.response.RespStatus;
import az.orient.eshopspring2.dto.response.RespWarehouse;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.entity.Shelf;
import az.orient.eshopspring2.entity.Warehouse;
import az.orient.eshopspring2.enums.EnumAvailableStatus;
import az.orient.eshopspring2.exception.EshopException;
import az.orient.eshopspring2.exception.ExceptionConstants;
import az.orient.eshopspring2.repository.ShelfRepository;
import az.orient.eshopspring2.repository.WarehouseRepository;
import az.orient.eshopspring2.service.ShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelfServiceImpl implements ShelfService {
    private final ShelfRepository shelfRepository;
    private final WarehouseRepository warehouseRepository;

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
            Shelf shelf = Shelf.builder()
                    .id(reqShelf.getId())
                    .warehouse(warehouse)
                    .name(name)
                    .build();
            shelfRepository.save(shelf);
            RespShelf respShelf = convert(shelf);
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
            List<RespShelf> respShelfList = shelfList.stream().map(this::convert).toList();
            response.setT(respShelfList);
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
            RespShelf respShelf = convert(shelf);
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
    public Response<RespShelf> updateShelf(ReqShelf reqShelf) {
        Response<RespShelf> response = new Response<>();
        try {
            Long id = reqShelf.getId();
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
            shelf.setName(reqShelf.getName());
            shelf.setWarehouse(warehouse);
            shelfRepository.save(shelf);
            RespShelf respShelf = convert(shelf);
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

    private RespShelf convert(Shelf shelf) {
        RespWarehouse respWarehouse = RespWarehouse.builder()
                .id(shelf.getWarehouse().getId())
                .name(shelf.getWarehouse().getName())
                .address(shelf.getWarehouse().getAddress())
                .build();
        return RespShelf.builder()
                .id(shelf.getId())
                .respWarehouse(respWarehouse)
                .name(shelf.getName())
                .build();
    }

}
