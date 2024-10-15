package az.orient.eshop.service;

import az.orient.eshop.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ImageService {
    Response addImages(Set<MultipartFile> files, Long productDetailsId);

    Response deleteImagesByProductDetailsId(Long productDetailsId);

    Response deleteImage(Long imageId);

    Set<ResponseEntity<byte[]>> getImages(Long productDetailsId);

    ResponseEntity<byte[]> getImage(Long imageId);
}
