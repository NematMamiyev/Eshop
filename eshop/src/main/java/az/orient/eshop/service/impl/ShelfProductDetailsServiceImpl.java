package az.orient.eshop.service.impl;


import az.orient.eshop.dto.request.ReqShelfProductDetails;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ShelfProductDetailsMapper;
import az.orient.eshop.repository.*;
import az.orient.eshop.service.ShelfProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelfProductDetailsServiceImpl implements ShelfProductDetailsService {
    private final ShelfProductRepository shelfProductRepository;
    private final ShelfRepository shelfRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ShelfProductDetailsMapper shelfProductDetailsMapper;

    @Override
    public Response<RespShelfProductDetails> addProductInShelf(ReqShelfProductDetails reqShelfProduct) {
        Response<RespShelfProductDetails> response = new Response<>();
        try {
            Long shelfId = reqShelfProduct.getShelfId();
            Long productDetailsId = reqShelfProduct.getProductDetailsId();
            if (shelfId == null || productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Shelf id or product details id is null");
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
          //  voloditaion yazanda!! rəfə əlavə edəndə yoxlasın belə bir productDetailsId əlavə edilib yoxsa edilmeyib silendə problem çixir
            ShelfProductDetails shelfProductDetails = shelfProductDetailsMapper.toShelfProductDetails(reqShelfProduct);
            shelfProductRepository.save(shelfProductDetails);
            RespShelfProductDetails respShelfProductDetails = shelfProductDetailsMapper.toRespShelfProductDetails(shelfProductDetails);
            response.setT(respShelfProductDetails);
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
    public Response deleteProductInShelf(ReqShelfProductDetails reqShelfProduct) {
        Response response = new Response<>();
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
            ShelfProductDetails shelfProductDetails = shelfProductRepository.findShelfProductByShelfIdAndProductDetailsIdAndActive(shelfId,productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            shelfProductDetails.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            shelfProductRepository.save(shelfProductDetails);
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
