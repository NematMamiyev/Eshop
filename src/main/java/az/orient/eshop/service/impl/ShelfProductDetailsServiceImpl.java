package az.orient.eshop.service.impl;


import az.orient.eshop.dto.request.ReqShelfProductDetails;
import az.orient.eshop.dto.response.RespShelfProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Shelf;
import az.orient.eshop.entity.ShelfProductDetails;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ShelfProductDetailsMapper;
import az.orient.eshop.repository.ShelfProductRepository;
import az.orient.eshop.repository.ShelfRepository;
import az.orient.eshop.service.ShelfProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelfProductDetailsServiceImpl implements ShelfProductDetailsService {
    private final ShelfProductRepository shelfProductRepository;
    private final ShelfRepository shelfRepository;
    private final ShelfProductDetailsMapper shelfProductDetailsMapper;

    @Override
    public Response<RespShelfProductDetails> addProductInShelf(ReqShelfProductDetails reqShelfProductDetails) {
        Response<RespShelfProductDetails> response = new Response<>();
        Shelf shelf = shelfRepository.findByIdAndActive(reqShelfProductDetails.getShelfId(), EnumAvailableStatus.ACTIVE.getValue());
        if (shelfProductRepository.existsByProductDetailsInWarehouse(reqShelfProductDetails.getProductDetailsId(), shelf.getWarehouse().getId())) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product already added to shelf");
        }
        ShelfProductDetails shelfProductDetails = shelfProductDetailsMapper.toShelfProductDetails(reqShelfProductDetails);
        shelfProductRepository.save(shelfProductDetails);
        response.setT(shelfProductDetailsMapper.toRespShelfProductDetails(shelfProductDetails));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public RespStatus deleteProductInShelf(ReqShelfProductDetails reqShelfProductDetails) {
        ShelfProductDetails shelfProductDetails = shelfProductRepository
                .findShelfProductByShelfIdAndProductDetailsIdAndActive(reqShelfProductDetails.getShelfId(),
                        reqShelfProductDetails.getProductDetailsId(), EnumAvailableStatus.ACTIVE.getValue());
        shelfProductDetails.setActive(EnumAvailableStatus.DEACTIVATED.getValue());
        shelfProductRepository.save(shelfProductDetails);
        return RespStatus.getSuccessMessage();
    }
}
