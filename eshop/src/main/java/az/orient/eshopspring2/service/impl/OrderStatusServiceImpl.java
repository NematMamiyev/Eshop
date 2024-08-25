package az.orient.eshopspring2.service.impl;

import az.orient.eshopspring2.dto.request.ReqOrderStatus;
import az.orient.eshopspring2.dto.response.RespOrderStatus;
import az.orient.eshopspring2.dto.response.RespStatus;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.entity.OrderStatus;
import az.orient.eshopspring2.enums.EnumAvailableStatus;
import az.orient.eshopspring2.exception.EshopException;
import az.orient.eshopspring2.exception.ExceptionConstants;
import az.orient.eshopspring2.repository.OrderStatusRepository;
import az.orient.eshopspring2.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    @Override
    public Response<RespOrderStatus> addOrderStatus(ReqOrderStatus reqOrderStatus) {
        Response<RespOrderStatus> response = new Response<>();
        try {
            String name = reqOrderStatus.getName();
            if (name == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            boolean uniqueName = orderStatusRepository.existsOrderStatusByNameAndActive(name, EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueName) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            OrderStatus orderStatus = OrderStatus.builder()
                    .id(reqOrderStatus.getId())
                    .name(name)
                    .build();
            orderStatusRepository.save(orderStatus);
            RespOrderStatus respOrderStatus = convert(orderStatus);
            response.setT(respOrderStatus);
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
    public Response<List<RespOrderStatus>> orderStatusList() {
        Response<List<RespOrderStatus>> response = new Response<>();
        try {
            List<OrderStatus> orderStatusList = orderStatusRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (orderStatusList.isEmpty()) {
                throw new EshopException(ExceptionConstants.STATUS_NOT_FOUND, "OrderStatus not found");
            }
            List<RespOrderStatus> respOrderStatusList = orderStatusList.stream().map(this::convert).toList();
            response.setT(respOrderStatusList);
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
    public Response<RespOrderStatus> getOrderStatusById(Long id) {
        Response<RespOrderStatus> response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            OrderStatus orderStatus = orderStatusRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (orderStatus == null) {
                throw new EshopException(ExceptionConstants.STATUS_NOT_FOUND, "OrderStatus not found");
            }
            RespOrderStatus respOrderStatus = convert(orderStatus);
            response.setT(respOrderStatus);
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
    public Response<RespOrderStatus> updateOrderStatus(ReqOrderStatus reqOrderStatus) {
        Response<RespOrderStatus> response = new Response<>();
        try {
            Long id = reqOrderStatus.getId();
            String name = reqOrderStatus.getName();
            if (id == null || name == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id or name is invalid");
            }
            OrderStatus orderStatus = orderStatusRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (orderStatus == null) {
                throw new EshopException(ExceptionConstants.STATUS_NOT_FOUND, "OrderStatus not found");
            }
            boolean uniqueName = orderStatusRepository.existsOrderStatusByNameAndActiveAndIdNot(name, EnumAvailableStatus.ACTIVE.getValue(), id);
            if (uniqueName) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Name available in the database");
            }
            orderStatus.setName(reqOrderStatus.getName());
            orderStatusRepository.save(orderStatus);
            RespOrderStatus respOrderStatus = convert(orderStatus);
            response.setT(respOrderStatus);
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
    public Response deleteOrderStatus(Long id) {
        Response response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
            }
            OrderStatus orderStatus = orderStatusRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (orderStatus == null) {
                throw new EshopException(ExceptionConstants.STATUS_NOT_FOUND, "OrderStatus not found");
            }
            orderStatus.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            orderStatusRepository.save(orderStatus);
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

    private RespOrderStatus convert(OrderStatus orderStatus) {
        return RespOrderStatus.builder()
                .id(orderStatus.getId())
                .name(orderStatus.getName())
                .build();
    }

}
