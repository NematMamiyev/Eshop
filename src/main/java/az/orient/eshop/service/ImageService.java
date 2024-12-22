package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ImageService {
    RespStatus addImages(List<MultipartFile> files, Long productDetailsId) throws IOException;

    RespStatus deleteImagesByProductDetailsId(Long productDetailsId);

    RespStatus deleteImage(Long imageId);

    List<ResponseEntity<byte[]>> getImages(Long productDetailsId);

    ResponseEntity<byte[]> getImage(Long imageId);
}
