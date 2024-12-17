package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqProduct;
import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Gender;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.ProductMapper;
import az.orient.eshop.repository.*;
import az.orient.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductMapper productMapper;

    @Override
    public Response<RespProduct> addProduct(ReqProduct reqProduct, List<ReqProductDetails> reqProductDetailsList) {
        Response<RespProduct> response = new Response<>();
            Product product = productMapper.toProduct(reqProduct);
            productRepository.save(product);
            List<ProductDetails> productDetailsList = reqProductDetailsList.stream().map(reqProductDetails ->
                    ProductDetails.builder()
                            .product(product)
                            .color(getColor(reqProductDetails.getColorId()))
                            .size(getSize(reqProductDetails.getSizeId()))
                            .stock(reqProductDetails.getStock())
                            .currency(reqProductDetails.getCurrency())
                            .price(reqProductDetails.getPrice())
                            .build()
            ).collect(Collectors.toList());
            productDetailsRepository.saveAll(productDetailsList);
            product.setProductDetailsList(productDetailsList);
            RespProduct respProduct = productMapper.toRespProduct(product);
            response.setT(respProduct);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<List<RespProduct>> getProductList() {
        Response<List<RespProduct>> response = new Response<>();
            List<Product> productList = productRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (productList.isEmpty()) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product is empty");
            }
            List<RespProduct> respProductList = productMapper.toRespProductList(productList);
            response.setT(respProductList);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespProduct> getProductById(Long id) {
        Response<RespProduct> response = new Response<>();
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, " Id is null");
            }
            Product product = productRepository.findProductByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            RespProduct respProduct = productMapper.toRespProduct(product);
            response.setT(respProduct);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespProduct> updateProduct(Long id, ReqProduct reqProduct) {
        Response<RespProduct> response = new Response<>();
            String name = reqProduct.getName();
            Long subcategoryId = reqProduct.getSubcategoryId();
            Gender.fromValue(reqProduct.getGender().getValue());
            Long brandId = reqProduct.getBrandId();
            if (id == null || name == null || brandId == null || subcategoryId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Product product = productRepository.findProductByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            getSubcategory(subcategoryId);
            productMapper.updateProductFromReqProduct(product,reqProduct);
            productRepository.save(product);
            RespProduct respProduct = productMapper.toRespProduct(product);
            response.setT(respProduct);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response deleteProduct(Long id) {
        Response response = new Response<>();
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            Product product = productRepository.findProductByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            product.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            productRepository.save(product);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    public Subcategory getSubcategory(Long subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findSubcategoryByIdAndActive(subcategoryId, EnumAvailableStatus.ACTIVE.getValue());
        if (subcategory == null) {
            throw new EshopException(ExceptionConstants.SUBCATEGORY_NOT_FOUND, "Subcategory not found");
        }
        return subcategory;
    }

    public Size getSize(Long sizeId) {
        return sizeRepository.findSizeByIdAndActive(sizeId, EnumAvailableStatus.ACTIVE.getValue());
    }

    public Color getColor(Long colorId) {
        return colorRepository.findByIdAndActive(colorId, EnumAvailableStatus.ACTIVE.getValue());
    }
}
