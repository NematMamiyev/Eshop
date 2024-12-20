package az.orient.eshop.controller;

import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;

    @PostMapping("/{productDetailsId}")
    public Response addVideos(@RequestParam(value = "file") List<MultipartFile> files, @PathVariable Long productDetailsId){
        return videoService.addVideos(files,productDetailsId);
    }

    @DeleteMapping("/list/{productDetailsId}")
    public Response deleteVideosByProductDetailsId(@PathVariable Long productDetailsId){
        return videoService.deleteVideosByProductDetailsId(productDetailsId);
    }

    @DeleteMapping("/{videoId}")
    public Response deleteVideo(@PathVariable Long videoId){
        return videoService.deleteVideo(videoId);
    }

    @GetMapping("/list/{productDetailsId}")
    public List<ResponseEntity<byte[]>> getVideos(@PathVariable Long productDetailsId){
        return videoService.getVideos(productDetailsId);
    }
    @GetMapping("/{videoId}")
    public ResponseEntity<byte[]> getVideo(@PathVariable Long videoId){
        return videoService.getVideo(videoId);
    }
}
