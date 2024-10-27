package az.orient.eshop.controller;

import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;

    @PostMapping("/{productDetailsId}")
    public Response addVideos(@RequestParam(value = "file") Set<MultipartFile> files, @PathVariable Long productDetailsId){
        return videoService.addVideos(files,productDetailsId);
    }

    @DeleteMapping("/{productDetailsId}")
    public Response deleteVideosByProductDetailsId(@PathVariable Long productDetailsId){
        return videoService.deleteVideosByProductDetailsId(productDetailsId);
    }

    @DeleteMapping("/{videoId}")
    public Response deleteVideo(@PathVariable Long videoId){
        return videoService.deleteVideo(videoId);
    }

    @GetMapping("/{productDetailsId}")
    public Set<ResponseEntity<byte[]>> getVideos(@PathVariable Long productDetailsId){
        return videoService.getVideos(productDetailsId);
    }
    @GetMapping("/{videoId}")
    public ResponseEntity<byte[]> getVideo(@PathVariable Long videoId){
        return videoService.getVideo(videoId);
    }
}
