package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.service.VideoService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;

    @PostMapping("/{productDetailsId}")
    public RespStatus addVideos(@RequestParam(value = "file") List<MultipartFile> files, @PathVariable @NotNull(message = "Id is required") Long productDetailsId) throws IOException {
        return videoService.addVideos(files,productDetailsId);
    }

    @DeleteMapping("/list/{productDetailsId}")
    public RespStatus deleteVideosByProductDetailsId(@PathVariable @NotNull(message = "Id is required") Long productDetailsId){
        return videoService.deleteVideosByProductDetailsId(productDetailsId);
    }

    @DeleteMapping("/{videoId}")
    public RespStatus deleteVideo(@PathVariable @NotNull(message = "Id is required") Long videoId){
        return videoService.deleteVideo(videoId);
    }

    @GetMapping("/list/{productDetailsId}")
    public List<ResponseEntity<byte[]>> getVideos(@PathVariable @NotNull(message = "Id is required") Long productDetailsId){
        return videoService.getVideos(productDetailsId);
    }
    @GetMapping("/{videoId}")
    public ResponseEntity<byte[]> getVideo(@PathVariable @NotNull(message = "Id is required") Long videoId){
        return videoService.getVideo(videoId);
    }
}
