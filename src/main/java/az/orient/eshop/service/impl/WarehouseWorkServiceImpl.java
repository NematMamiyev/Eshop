package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.Email;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Status;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.WarehouseWorkMapper;
import az.orient.eshop.repository.OrderStatusRepository;
import az.orient.eshop.repository.WarehouseWorkRepository;
import az.orient.eshop.service.WarehouseWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseWorkServiceImpl implements WarehouseWorkService {

    private final WarehouseWorkRepository warehouseWorkRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final EmailServiceImpl emailService;
    private final WarehouseWorkMapper warehouseWorkMapper;

    @Override
    public Response<List<RespWareHouseWork>> works() {
        Response<List<RespWareHouseWork>> response = new Response<>();
            List<WarehouseWork> warehouseWorkList = warehouseWorkRepository.findALLByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (warehouseWorkList.isEmpty()) {
                throw new EshopException(ExceptionConstants.WAREHOUSE_WORK_NOT_FOUND, "Warehouse work not found");
            }
            List<RespWareHouseWork> respWareHouseWorkList = warehouseWorkMapper.toRespWarehouseWorkList(warehouseWorkList);
            response.setT(respWareHouseWorkList);
            response.setStatus(RespStatus.getSuccessMessage());
       return response;
    }

    @Override
    public Response<RespWareHouseWork> handleWork(Long id) {
        Response<RespWareHouseWork> response = new Response<>();
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Id is null");
            }
            WarehouseWork warehouseWork = warehouseWorkRepository.findWarehouseWorkByIdAndActive(id,EnumAvailableStatus.ACTIVE.getValue());
            if (warehouseWork == null){
                throw new EshopException(ExceptionConstants.WAREHOUSE_WORK_NOT_FOUND,"Warehouse work not found");
            }
            warehouseWork.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            OrderStatus orderStatus = OrderStatus.builder()
                    .order(warehouseWork.getOrder())
                    .status(Status.CONFIRMED)
                    .build();
            orderStatusRepository.save(orderStatus);
            RespWareHouseWork respWareHouseWork = warehouseWorkMapper.toRespWarehouseWork(warehouseWork);
            response.setT(respWareHouseWork);
            response.setStatus(RespStatus.getSuccessMessage());
            warehouseWork.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            emailService.sendSimpleEmail(warehouseWork.getOrder().getCustomer().getEmail(), "Mehsul", Email.CONFIRMED.getDescription());
        return response;
    }
}
