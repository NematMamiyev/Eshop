package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.entity.ProductImage;
import az.orient.eshop.entity.ProductVideo;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.MediaTypeEnum;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.ProductImageRepository;
import az.orient.eshop.repository.ProductVideoRepository;
import az.orient.eshop.service.MediaService;
import az.orient.eshop.util.ImageUtility;
import az.orient.eshop.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final ProductImageRepository productImageRepository;
    private final ProductVideoRepository productVideoRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final Utility utility = new Utility();

    @Override
    public Response addMedias(Set<MultipartFile> files, Long productDetailsId, MediaTypeEnum mediaTypeEnum) {
        Response response = new Response<>();
        try {
            if (files.isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, mediaTypeEnum + " is empty");
            }
            if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            if (mediaTypeEnum == MediaTypeEnum.IMAGE) {
                for (MultipartFile file : files) {
                    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                    if (fileName.contains("..")) {
                        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Filename contains invalid path sequence");
                    }
                    ProductImage productImage = new ProductImage(fileName, file.getContentType(), file.getBytes(), productDetails);
                    productImageRepository.save(productImage);
                }
            } else if (mediaTypeEnum == MediaTypeEnum.VIDEO) {
                for (MultipartFile file : files) {
                    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                    if (fileName.contains("..")) {
                        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Filename contains invalid path sequence");
                    }
                    ProductVideo productVideo = new ProductVideo(fileName, file.getContentType(), file.getBytes(), productDetails);
                    productVideoRepository.save(productVideo);
                }
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
    public Response deleteMediasByProductDetailsId(Long productDetailsId, MediaTypeEnum mediaTypeEnum) {
        Response response = new Response<>();
        try {
            if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            MediaTypeEnum.fromValue(mediaTypeEnum.name());
            if (mediaTypeEnum == MediaTypeEnum.IMAGE) {
                Set<ProductImage> images = productImageRepository.findProductImageByProductDetailsIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
                if (images.isEmpty()) {
                    throw new EshopException(ExceptionConstants.PRODUCT_IMAGE_NOT_FOUND, "Image is empty");
                }
                productImageRepository.deactivateProductImagesByProductDetailsId(productDetailsId);
                productImageRepository.saveAll(images);
            } else if (mediaTypeEnum == MediaTypeEnum.VIDEO) {
                Set<ProductVideo> videos = productVideoRepository.findProductVideoByProductDetailsIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
                if (videos.isEmpty()) {
                    throw new EshopException(ExceptionConstants.PRODUCT_VIDEO_NOT_FOUND, "Video is empty");
                }
                productVideoRepository.deactivateProductVideoByProductDetailsId(productDetailsId);
                productVideoRepository.saveAll(videos);
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
    public Response addMedia(MultipartFile file, Long productDetailsId, MediaTypeEnum mediaTypeEnum) {
        Response response = new Response<>();
        try {
            if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Filename contains invalid path sequence");
            }
            if (mediaTypeEnum == MediaTypeEnum.IMAGE) {
                ProductImage productImage = new ProductImage(fileName, file.getContentType(), file.getBytes(), productDetails);
                productImageRepository.save(productImage);
            } else if (mediaTypeEnum == MediaTypeEnum.VIDEO) {
                ProductVideo productVideo = new ProductVideo(fileName, file.getContentType(), file.getBytes(), productDetails);
                productVideoRepository.save(productVideo);
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
    public Response deleteMediaId(Long mediaId, MediaTypeEnum mediaTypeEnum) {
        Response response = new Response<>();
        try {
            if (mediaId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Image id is null");
            }
            if (mediaTypeEnum == MediaTypeEnum.IMAGE) {
                ProductImage productImage = productImageRepository.findProductImageByIdAndActive(mediaId, EnumAvailableStatus.ACTIVE.getValue());
                if (productImage == null) {
                    throw new EshopException(ExceptionConstants.PRODUCT_IMAGE_NOT_FOUND, "Image not found");
                }
                productImage.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                productImageRepository.save(productImage);
            } else if (mediaTypeEnum == MediaTypeEnum.VIDEO) {
                ProductVideo productVideo = productVideoRepository.findProductVideoByIdAndActive(mediaId, EnumAvailableStatus.ACTIVE.getValue());
                if (productVideo == null) {
                    throw new EshopException(ExceptionConstants.PRODUCT_VIDEO_NOT_FOUND, "Video not found");
                }
                productVideo.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                productVideoRepository.save(productVideo);
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
    public List<ResponseEntity<byte[]>> getMedias(Long productDetailsId) {
        List<ResponseEntity<byte[]>> response = new ArrayList<>();
        try {
            if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            List<ProductImage> productImageList = productImageRepository.findAllByProductDetailsIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            for (ProductImage productImage: productImageList){
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
    public ResponseEntity<byte[]> getMedia(Long imageId, MediaTypeEnum mediaTypeEnum) {
        ResponseEntity response = null;
        try {
            if (imageId == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Id is null");
            }
            if (mediaTypeEnum == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Mediatype is null");
            }
            MediaTypeEnum.fromValue(mediaTypeEnum.name());
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
