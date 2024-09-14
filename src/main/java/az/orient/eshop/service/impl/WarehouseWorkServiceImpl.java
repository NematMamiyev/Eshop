package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Status;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.OrderStatusRepository;
import az.orient.eshop.repository.WarehouseWorkRepository;
import az.orient.eshop.service.WarehouseWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseWorkServiceImpl implements WarehouseWorkService {

    private final WarehouseWorkRepository warehouseWorkRepository;
    private final OrderStatusRepository orderStatusRepository;

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
        List<RespProductDetails> respProductDetailsList = warehouseWork.getOrder().getProductDetailsList().stream().map(this::convertToRespProductDetails).toList();
        RespCustomer respCustomer = RespCustomer.builder()
                .id(warehouseWork.getOrder().getCustomer().getId())
                .name(warehouseWork.getOrder().getCustomer().getName())
                .surname(warehouseWork.getOrder().getCustomer().getSurname())
                .address(warehouseWork.getOrder().getCustomer().getAddress())
                .phone(warehouseWork.getOrder().getCustomer().getPhone())
                .build();
        RespOrder respOrder = RespOrder.builder()
                .id(warehouseWork.getOrder().getId())
                .respCustomer(respCustomer)
                .respProductDetailsList(respProductDetailsList)
                .amount(warehouseWork.getOrder().getAmount())
                .build();
        return RespWareHouseWork.builder()
                .id(warehouseWork.getId())
                .respOrder(respOrder)
                .build();
    }
    private RespProductDetails convertToRespProductDetails(ProductDetails productDetails) {
        Set<RespProductImage> respProductImages =productDetails.getImages().stream().map(this::convertToRespProductImage).collect(Collectors.toSet());
        Set<RespProductVideo> respProductVideos = productDetails.getVideos().stream().map(this::convertToRespProductVideo).collect(Collectors.toSet());
        RespSize respSize = RespSize.builder()
                .id(productDetails.getSize().getId())
                .name(productDetails.getSize().getName())
                .build();
        RespColor respColor = RespColor.builder()
                .id(productDetails.getColor().getId())
                .name(productDetails.getColor().getName())
                .build();
        return RespProductDetails.builder()
                .id(productDetails.getId())
                .respSize(respSize)
                .respColor(respColor)
                .currency(productDetails.getCurrency())
                .price(productDetails.getPrice())
                .stock(productDetails.getStock())
                .respProductVideoList(respProductVideos)
                .respProductImageList(respProductImages)
                .build();
    }
    private RespProductImage convertToRespProductImage(ProductImage productImage) {
        return RespProductImage.builder()
                .data(productImage.getData())
                .fileName(productImage.getFileName())
                .fileType(productImage.getFileType())
                .build();
    }

    private RespProductVideo convertToRespProductVideo(ProductVideo productVideo) {
        return RespProductVideo.builder()
                .data(productVideo.getData())
                .fileName(productVideo.getFileName())
                .fileType(productVideo.getFileType())
                .build();
    }
}
