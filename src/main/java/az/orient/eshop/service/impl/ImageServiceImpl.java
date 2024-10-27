package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.entity.ProductImage;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.ProductImageRepository;
import az.orient.eshop.repository.ProductVideoRepository;
import az.orient.eshop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductVideoRepository productVideoRepository;
    private final ProductDetailsRepository productDetailsRepository;

    @Override
    public Response addImages(Set<MultipartFile> files, Long productDetailsId) {
        Response response = new Response<>();
        try {
            if (files.isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "file is empty");
            }
            if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                if (fileName.contains("..")) {
                    throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Filename contains invalid path sequence");
                }
                ProductImage productImage = new ProductImage(fileName, file.getContentType(), file.getBytes(), productDetails);
                productImageRepository.save(productImage);
            }
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
    public Response deleteImagesByProductDetailsId(Long productDetailsId) {
        Response response = new Response<>();
        try {
            if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            Set<ProductImage> images = productImageRepository.findProductImageByProductDetailsIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (images.isEmpty()) {
                throw new EshopException(ExceptionConstants.PRODUCT_IMAGE_NOT_FOUND, "Image is empty");
            }
            productImageRepository.deactivateProductImagesByProductDetailsId(productDetailsId);
            productImageRepository.saveAll(images);
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
    public Response deleteImage(Long imageId) {
        Response response = new Response<>();
        try {
            if (imageId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Image id is null");
            }

            ProductImage productImage = productImageRepository.findProductImageByIdAndActive(imageId, EnumAvailableStatus.ACTIVE.getValue());
            if (productImage == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_IMAGE_NOT_FOUND, "Image not found");
            }
            productImage.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            productImageRepository.save(productImage);
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
    public Set<ResponseEntity<byte[]>> getImages(Long productDetailsId) {
        Set<ResponseEntity<byte[]>> response = new HashSet<>();
        try {
            if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            Set<ProductImage> productImages = productImageRepository.findProductImageByProductDetailsIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            for (ProductImage productImage: productImages){
                ResponseEntity<byte[]> responseEntity = ResponseEntity.ok()
                        .contentType(MediaType.valueOf(productImage.getFileType()))
                        .body(productImage.getData());
                response.add(responseEntity);
            }
        } catch (EshopException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long imageId) {
        ResponseEntity response = null;
        try {
            if (imageId == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Id is null");
            }
            ProductImage productImage = productImageRepository.findProductImageByIdAndActive(imageId,EnumAvailableStatus.ACTIVE.getValue());
            if (productImage == null){
                throw new EshopException(ExceptionConstants.PRODUCT_IMAGE_NOT_FOUND,"Product Image Not found");
            }
            response = ResponseEntity.ok()
                    .contentType(MediaType.valueOf(productImage.getFileType()))
                    .body(productImage.getData());
        }catch (EshopException ex) {
            ex.printStackTrace();
            new  RespStatus(ex.getCode(), ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
