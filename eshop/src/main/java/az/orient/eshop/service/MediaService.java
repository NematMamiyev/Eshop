package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespMedia;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.enums.MediaTypeEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface MediaService {
    Response addMedias(Set<MultipartFile> files, Long productDetailsId, MediaTypeEnum mediaTypeEnum);

    Response deleteMediasByProductDetailsId(Long productDetailsId, MediaTypeEnum mediaTypeEnum);

    Response addMedia(MultipartFile file, Long productDetailsId, MediaTypeEnum mediaTypeEnum);

    Response deleteMediaId(Long mediaId, MediaTypeEnum mediaTypeEnum);

    List<ResponseEntity<byte[]>> getMedias(Long productDetailsId);

    ResponseEntity<byte[]> getMedia(Long imageId, MediaTypeEnum mediaTypeEnum);
}
