package az.orient.eshop.service;

import az.orient.eshop.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ImageService {
    Response addImages(List<MultipartFile> files, Long productDetailsId) throws IOException;

    Response deleteImagesByProductDetailsId(Long productDetailsId);

    Response deleteImage(Long imageId);

    List<ResponseEntity<byte[]>> getImages(Long productDetailsId);

    ResponseEntity<byte[]> getImage(Long imageId);
}
