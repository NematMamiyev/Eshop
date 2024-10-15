package az.orient.eshop.service;

import az.orient.eshop.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface VideoService {
    Response addVideos(Set<MultipartFile> files, Long productDetailsId);

    Response deleteVideosByProductDetailsId(Long productDetailsId);

    Response deleteVideo(Long videoId);

    Set<ResponseEntity<byte[]>> getVideos(Long productDetailsId);

    ResponseEntity<byte[]> getVideo(Long videoId);
}
