package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.Email;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Status;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.OrderStatusRepository;
import az.orient.eshop.repository.WarehouseWorkRepository;
import az.orient.eshop.service.WarehouseWorkService;
import az.orient.eshop.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseWorkServiceImpl implements WarehouseWorkService {

    private final WarehouseWorkRepository warehouseWorkRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final Utility utility = new Utility();
    private final EmailServiceImpl emailService;

    @Override
    public Response<List<RespWareHouseWork>> works() {
        Response<List<RespWareHouseWork>> response = new Response<>();
        try {
            List<WarehouseWork> warehouseWorkList = warehouseWorkRepository.findALLByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (warehouseWorkList.isEmpty()) {
                throw new EshopException(ExceptionConstants.WAREHOUSE_WORK_NOT_FOUND, "Warehouse work not found");
            }
            List<RespWareHouseWork> respWareHouseWorkList = warehouseWorkList.stream().map(this::convert).toList();
            response.setT(respWareHouseWorkList);
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
    public Response<RespWareHouseWork> handleWork(Long id) {
        Response<RespWareHouseWork> response = new Response<>();
        try {
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
            RespWareHouseWork respWareHouseWork = convert(warehouseWork);
            response.setT(respWareHouseWork);
            response.setStatus(RespStatus.getSuccessMessage());
            warehouseWork.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            emailService.sendSimpleEmail(warehouseWork.getOrder().getCustomer().getEmail(), "Mehsul", Email.CONFIRMED.getDescription());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    private RespWareHouseWork convert(WarehouseWork warehouseWork) {
        List<RespProductDetails> respProductDetailsList = warehouseWork.getOrder().getProductDetailsList().stream().map(utility::convertToRespProductDetails).toList();
        RespOrder respOrder = RespOrder.builder()
                .id(warehouseWork.getOrder().getId())
                .respProductDetailsList(respProductDetailsList)
                .amount(warehouseWork.getOrder().getAmount())
                .build();
        return RespWareHouseWork.builder()
                .id(warehouseWork.getId())
                .respOrder(respOrder)
                .dataDate(warehouseWork.getDataDate())
                .build();
    }

}
