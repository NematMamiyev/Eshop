package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespMedia;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.enums.MediaTypeEnum;
import az.orient.eshop.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/medias")
public class MediaController {
    private final MediaService mediaService;

    @PostMapping("/addMedias/{id}/{mediaTypeEnum}")
    public Response addMedias(@RequestParam(value = "file") Set<MultipartFile> files, @PathVariable(value = "id") Long productDetailsId, @PathVariable MediaTypeEnum mediaTypeEnum){
        return mediaService.addMedias(files,productDetailsId, mediaTypeEnum);
    }

    @DeleteMapping("/deleteMedias/{id}/{mediaType}")
    public Response deleteMediasByProductDetailsId(@PathVariable(value = "id") Long productDetailsId,@PathVariable(value = "mediaType") MediaTypeEnum mediaTypeEnum){
        return mediaService.deleteMediasByProductDetailsId(productDetailsId, mediaTypeEnum);
    }

    @PostMapping("/addMedia/{id}/{mediaTypeEnum}")
    public Response addMedia(@RequestParam(value = "file") MultipartFile file, @PathVariable(value = "id") Long productDetailsId, @PathVariable MediaTypeEnum mediaTypeEnum){
        return mediaService.addMedia(file,productDetailsId, mediaTypeEnum);
    }

    @DeleteMapping("/deleteMedia/{id}/{mediaType}")
    public Response deleteMediaId(@PathVariable(value = "id") Long mediaId,@PathVariable(value = "mediaType") MediaTypeEnum mediaTypeEnum){
        return mediaService.deleteMediaId(mediaId, mediaTypeEnum);
    }

    @GetMapping("/getMedias/{id}")
    public List<ResponseEntity<byte[]>> getMedias(@PathVariable(value = "id") Long productDetailsId){
        return mediaService.getMedias(productDetailsId);
    }
    @GetMapping("/getMedia/{id}/{mediaType}")
    public ResponseEntity<byte[]> getMedia(@PathVariable(value = "id") Long imageId,@PathVariable(value = "mediaType") MediaTypeEnum mediaTypeEnum){
        return mediaService.getMedia(imageId,mediaTypeEnum);
    }

}
