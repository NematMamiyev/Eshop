package az.orient.eshop.service.impl;


import az.orient.eshop.dto.request.ReqShelfProduct;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.Product;
import az.orient.eshop.entity.Shelf;
import az.orient.eshop.entity.ShelfProduct;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.ProductRepository;
import az.orient.eshop.repository.ShelfProductRepository;
import az.orient.eshop.repository.ShelfRepository;
import az.orient.eshop.repository.WarehouseRepository;
import az.orient.eshop.service.ShelfProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelfProductServiceImpl implements ShelfProductService {
    private final ShelfProductRepository shelfProductRepository;
    private final ShelfRepository shelfRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public Response<RespShelfProduct> addProductInShelf(ReqShelfProduct reqShelfProduct) {
        Response<RespShelfProduct> response = new Response<>();
        try {
            Long shelfId = reqShelfProduct.getShelfId();
            Long productId = reqShelfProduct.getProductId();
            if (shelfId == null || productId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Sheld id or product id is null");
            }
            Shelf shelf = shelfRepository.findByIdAndActive(shelfId, EnumAvailableStatus.ACTIVE.getValue());
            if (shelf == null) {
                throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
            }
            Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            Long warehouseId = shelf.getWarehouse().getId();
            boolean uniqueProductInShelf =shelfProductRepository.existsShelfProductByProductIdAndWarehouseIdAndActive(productId,warehouseId,EnumAvailableStatus.ACTIVE.getValue());
            if (uniqueProductInShelf) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product already added to shelf");
            }
            ShelfProduct shelfProduct = ShelfProduct.builder()
                    .id(reqShelfProduct.getId())
                    .shelf(shelf)
                    .product(product)
                    .warehouse(warehouseRepository.findWarehouseByIdAndActive(warehouseId,EnumAvailableStatus.ACTIVE.getValue()))
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
            Long productId = reqShelfProduct.getProductId();
            if (shelfId == null || productId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Sheld id or product id is null");
            }
            Shelf shelf = shelfRepository.findByIdAndActive(shelfId, EnumAvailableStatus.ACTIVE.getValue());
            if (shelf == null) {
                throw new EshopException(ExceptionConstants.SHELF_NOT_FOUND, "Shelf not found");
            }
            Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }

            ShelfProduct shelfProduct = shelfProductRepository.findShelfProductByShelfIdAndProductIdAndActive(shelfId,productId,EnumAvailableStatus.ACTIVE.getValue());
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
        RespProduct respProduct = RespProduct.builder()
                .id(shelfProduct.getProduct().getId())
                .name(shelfProduct.getProduct().getName())
                .count(shelfProduct.getProduct().getStock())
                .productNumber(shelfProduct.getProduct().getProductNumber())
                .expertionDate(shelfProduct.getProduct().getExpertionDate())
                .build();
        return RespShelfProduct.builder()
                .id(shelfProduct.getId())
                .respWarehouse(respWarehouse)
                .respShelf(respShelf)
                .respProduct(respProduct)
                .build();
    }
}
