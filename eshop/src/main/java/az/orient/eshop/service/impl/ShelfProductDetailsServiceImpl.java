package az.orient.eshop.service.impl;


import az.orient.eshop.dto.request.ReqShelfProduct;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.*;
import az.orient.eshop.service.ShelfProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelfProductDetailsServiceImpl implements ShelfProductService {
    private final ShelfProductRepository shelfProductRepository;
    private final ShelfRepository shelfRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public Response<RespShelfProduct> addProductInShelf(ReqShelfProduct reqShelfProduct) {
        Response<RespShelfProduct> response = new Response<>();
        try {
            Long shelfId = reqShelfProduct.getShelfId();
            Long productDetailsId = reqShelfProduct.getProductDetailsId();
            if (shelfId == null || productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Sheld id or product details id is null");
            }
            Shelf shelf = shelfRepository.findByIdAndActive(shelfId, EnumAvailableStatus.ACTIVE.getValue());
            if (shelf == null) {
                throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            Long warehouseId = shelf.getWarehouse().getId();
            boolean uniqueProductInShelf =shelfProductRepository.existsShelfProductByProductDetailsIdAndWarehouseIdAndActive(productDetailsId,warehouseId,EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueProductInShelf) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product already added to shelf");
            }
            Warehouse warehouse = warehouseRepository.findWarehouseByIdAndActive(warehouseId,EnumAvailableStatus.ACTIVE.getValue());
            ShelfProduct shelfProduct = ShelfProduct.builder()
                    .id(reqShelfProduct.getId())
                    .shelf(shelf)
                    .productDetails(productDetails)
                    .warehouse(warehouse)
                    .build();
            shelfProductRepository.save(shelfProduct);
            RespShelfProduct respShelfProduct = convert(shelfProduct);
            response.setT(respShelfProduct);
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
    public Response<RespShelfProduct> deleteProductInShelf(ReqShelfProduct reqShelfProduct) {
        Response<RespShelfProduct> response = new Response<>();
        try {
            Long shelfId = reqShelfProduct.getShelfId();
            Long productDetailsId = reqShelfProduct.getProductDetailsId();
            if (shelfId == null || productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Sheld id or product id is null");
            }
            Shelf shelf = shelfRepository.findByIdAndActive(shelfId, EnumAvailableStatus.ACTIVE.getValue());
            if (shelf == null) {
                throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            ShelfProduct shelfProduct = shelfProductRepository.findShelfProductByShelfIdAndProductDetailsIdAndActive(shelfId,productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            shelfProduct.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            shelfProductRepository.save(shelfProduct);
            RespShelfProduct respShelfProduct = convert(shelfProduct);
            response.setT(respShelfProduct);
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

    private RespShelfProduct convert(ShelfProduct shelfProduct) {
        RespWarehouse respWarehouse = RespWarehouse.builder()
                .id(shelfProduct.getWarehouse().getId())
                .name(shelfProduct.getWarehouse().getName())
                .build();
        RespShelf respShelf = RespShelf.builder()
                .id(shelfProduct.getShelf().getId())
                .name(shelfProduct.getShelf().getName())
                .build();
        RespColor respColor = RespColor.builder()
                .id(shelfProduct.getProductDetails().getColor().getId())
                .name(shelfProduct.getProductDetails().getColor().getName())
                .build();
        RespSize respSize = RespSize.builder()
                .id(shelfProduct.getProductDetails().getSize().getId())
                .name(shelfProduct.getProductDetails().getSize().getName())
                .build();
        RespProduct respProduct = RespProduct.builder()
                .id(shelfProduct.getProductDetails().getProduct().getId())
                .name(shelfProduct.getProductDetails().getProduct().getName())
                .expertionDate(shelfProduct.getProductDetails().getProduct().getExpertionDate())
                .gender(shelfProduct.getProductDetails().getProduct().getGender())
                .build();
        RespProductDetails respProductDetails = RespProductDetails.builder()
                .stock(shelfProduct.getProductDetails().getStock())
                .respColor(respColor)
                .respSize(respSize)
                .respProduct(respProduct)
                .build();
        return RespShelfProduct.builder()
                .id(shelfProduct.getId())
                .respWarehouse(respWarehouse)
                .respShelf(respShelf)
                .respProductDetails(respProductDetails)
                .build();
    }
}
