package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.entity.ProductVideo;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.ProductVideoRepository;
import az.orient.eshop.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final ProductVideoRepository productVideoRepository;
    private final ProductDetailsRepository productDetailsRepository;

    @Override
    public Response addVideos(List<MultipartFile> files, Long productDetailsId) throws IOException {
        Response response = new Response<>();
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
                ProductVideo productVideo = new ProductVideo(fileName, file.getContentType(), file.getBytes(), productDetails);
                productVideoRepository.save(productVideo);
            }
            response.setStatus(RespStatus.getSuccessMessage());
            return response;
    }

    @Override
    public Response deleteVideosByProductDetailsId(Long productDetailsId) {
        Response response = new Response<>();
           if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }

            List<ProductVideo> videos = productVideoRepository.findProductVideoByProductDetailsIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (videos.isEmpty()) {
                throw new EshopException(ExceptionConstants.PRODUCT_VIDEO_NOT_FOUND, "Video is empty");
            }
            productVideoRepository.deactivateProductVideoByProductDetailsId(productDetailsId);
            productVideoRepository.saveAll(videos);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response deleteVideo(Long videoId) {
        Response response = new Response<>();
           if (videoId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Video id is null");
            }
            ProductVideo productVideo = productVideoRepository.findProductVideoByIdAndActive(videoId, EnumAvailableStatus.ACTIVE.getValue());
            if (productVideo == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_VIDEO_NOT_FOUND, "Video not found");
            }
            productVideo.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            productVideoRepository.save(productVideo);
            response.setStatus(RespStatus.getSuccessMessage());
       return response;
    }

    @Override
    public List<ResponseEntity<byte[]>> getVideos(Long productDetailsId) {
        List<ResponseEntity<byte[]>> response = new ArrayList<>();
           if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            List<ProductVideo> productVideos = productVideoRepository.findProductVideoByProductDetailsIdAndActive(productDetailsId,EnumAvailableStatus.ACTIVE.getValue());
            for (ProductVideo productVideo: productVideos){
                ResponseEntity<byte[]> responseEntity = ResponseEntity.ok()
                        .contentType(MediaType.valueOf(productVideo.getFileType()))
                        .body(productVideo.getData());
                response.add(responseEntity);
            }
      return response;
    }

    @Override
    public ResponseEntity<byte[]> getVideo(Long videoId) {
        ResponseEntity response = null;
           if (videoId == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Id is null");
            }
            ProductVideo productVideo= productVideoRepository.findProductVideoByIdAndActive(videoId,EnumAvailableStatus.ACTIVE.getValue());
            if (productVideo == null){
                throw new EshopException(ExceptionConstants.PRODUCT_VIDEO_NOT_FOUND,"Product video not found");
            }
            response = ResponseEntity.ok()
                    .contentType(MediaType.valueOf(productVideo.getFileType()))
                    .body(productVideo.getData());
        return response;
    }
}
