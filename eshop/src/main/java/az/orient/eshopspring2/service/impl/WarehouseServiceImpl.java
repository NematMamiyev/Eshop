package az.orient.eshopspring2.service.impl;

import az.orient.eshopspring2.dto.request.ReqWarehouse;
import az.orient.eshopspring2.dto.response.RespStatus;
import az.orient.eshopspring2.dto.response.RespWarehouse;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.entity.Warehouse;
import az.orient.eshopspring2.enums.EnumAvailableStatus;
import az.orient.eshopspring2.exception.EshopException;
import az.orient.eshopspring2.exception.ExceptionConstants;
import az.orient.eshopspring2.repository.WarehouseRepository;
import az.orient.eshopspring2.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    @Override
    public Response<RespWarehouse> addWarehouse(ReqWarehouse reqWarehouse) {
        Response<RespWarehouse> response = new Response<>();
        try {
            String name = reqWarehouse.getName();
            String address = reqWarehouse.getAddress();
            if (name == null || address == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Name or address is null");
            }
            boolean uniqueName = warehouseRepository.existsWarehouseByNameAndActive(name, EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Name available in the database");
            }
            Warehouse warehouse = Warehouse.builder()
                    .id(reqWarehouse.getId())
                    .name(name)
                    .address(address)
                    .build();
            warehouseRepository.save(warehouse);
            RespWarehouse respWarehouse = convert(warehouse);
            response.setT(respWarehouse);
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
    public Response<List<RespWarehouse>> getWarehouseList() {
        Response<List<RespWarehouse>> response = new Response<>();
        try {
            List<Warehouse> warehouseList = warehouseRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (warehouseList.isEmpty()){
                throw new EshopException(ExceptionConstants.WAREHOUSE_NOT_FOUND, "Warehouse not found");
            }
            List<RespWarehouse> respWarehouseList = warehouseList.stream().map(this::convert).toList();
            response.setT(respWarehouseList);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespWarehouse> getWarehouseById(Long id) {
        Response<RespWarehouse> response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Id is null");
            }
            Warehouse warehouse = warehouseRepository.findWarehouseByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (warehouse== null){
                throw new EshopException(ExceptionConstants.WAREHOUSE_NOT_FOUND,"Warehouse not found");
            }
            RespWarehouse respWarehouse = convert(warehouse);
            response.setT(respWarehouse);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespWarehouse> updateWarehouse(ReqWarehouse reqWarehouse) {
        Response<RespWarehouse> response = new Response<>();
        try {
            Long id = reqWarehouse.getId();
            String name = reqWarehouse.getName();
            String address = reqWarehouse.getAddress();
            if (id == null || name == null || address == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data");
            }
            boolean uniqueName = warehouseRepository.existsWarehouseByNameAndActiveAndIdNot(name, EnumAvailableStatus.ACTIVE.getValue(),id);
            if (uniqueName){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Name available in the database");
            }
            Warehouse warehouse = warehouseRepository.findWarehouseByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (warehouse == null){
                throw new EshopException(ExceptionConstants.WAREHOUSE_NOT_FOUND, "Warehouse not found");
            }
            warehouse.setName(name);
            warehouse.setAddress(address);
            warehouseRepository.save(warehouse);
            RespWarehouse respWarehouse = convert(warehouse);
            response.setT(respWarehouse);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response deleteWarehouse(Long id) {
        Response response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Id id null");
            }
            Warehouse warehouse = warehouseRepository.findWarehouseByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (warehouse == null){
                throw new EshopException(ExceptionConstants.WAREHOUSE_NOT_FOUND, "Warehouse not found");
            }
            warehouse.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            warehouseRepository.save(warehouse);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    private RespWarehouse convert(Warehouse warehouse){
        return RespWarehouse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .address(warehouse.getAddress())
                .build();
    }
}
