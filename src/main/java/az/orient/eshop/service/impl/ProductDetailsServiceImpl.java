package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ProductDetailsMapper;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsRepository productDetailsRepository;
    private final ProductDetailsMapper productDetailsMapper;

    @Override
    public Response<RespProductDetails> addProductDetails(ReqProductDetails reqProductDetails) {
        Response<RespProductDetails> response = new Response<>();
            ProductDetails productDetails = productDetailsMapper.toProductDetails(reqProductDetails);
            productDetailsRepository.save(productDetails);
            response.setT(productDetailsMapper.toRespProductDetails(productDetails));
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespProductDetails> updateProductDetails(Long id, ReqProductDetails reqProductDetails) {
        Response<RespProductDetails> response = new Response<>();
            ProductDetails productDetails = getProductDetails(id);
            productDetailsMapper.updateProductDetailsFromReqProductDetails(productDetails,reqProductDetails);
            productDetailsRepository.save(productDetails);
            response.setT(productDetailsMapper.toRespProductDetails(productDetails));
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<List<RespProductDetails>> getProductDetails() {
        Response<List<RespProductDetails>> response = new Response<>();
            List<ProductDetails> productDetailsList = productDetailsRepository.findProductDetailsByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (productDetailsList.isEmpty()) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product Details not found");
            }
            response.setT(productDetailsMapper.toRespProductDetailsList(productDetailsList));
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespProductDetails> getProductDetailsById(Long id) {
        Response<RespProductDetails> response = new Response<>();
            ProductDetails productDetails = getProductDetails(id);
            response.setT(productDetailsMapper.toRespProductDetails(productDetails));
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public RespStatus deleteProductDetails(Long id) {
            ProductDetails productDetails = getProductDetails(id);
            productDetails.setActive(EnumAvailableStatus.DEACTIVATED.getValue());
            productDetailsRepository.save(productDetails);
        return RespStatus.getSuccessMessage();
    }

    private ProductDetails getProductDetails(Long id){
        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
        if (productDetails == null) {
            throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
        }
        return productDetails;
    }

}
