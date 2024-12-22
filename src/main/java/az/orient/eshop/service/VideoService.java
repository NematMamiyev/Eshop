package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    RespStatus addVideos(List<MultipartFile> files, Long productDetailsId) throws IOException;

    RespStatus deleteVideosByProductDetailsId(Long productDetailsId);

    RespStatus deleteVideo(Long videoId);

    List<ResponseEntity<byte[]>> getVideos(Long productDetailsId);

    ResponseEntity<byte[]> getVideo(Long videoId);
}
