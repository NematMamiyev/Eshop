package az.orient.eshop.controller;

import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/{productDetailsId}")
    public Response addImages(@RequestParam(value = "file") Set<MultipartFile> files, @PathVariable Long productDetailsId){
        return imageService.addImages(files,productDetailsId);
    }

    @DeleteMapping("/{productDetailsId}")
    public Response deleteImagesByProductDetailsId(@PathVariable Long productDetailsId){
        return imageService.deleteImagesByProductDetailsId(productDetailsId);
    }

    @DeleteMapping("/{imageId}")
    public Response deleteImage(@PathVariable Long imageId){
        return imageService.deleteImage(imageId);
    }

    @GetMapping("/list/{productDetailsId}")
    public Set<ResponseEntity<byte[]>> getImages(@PathVariable Long productDetailsId){
        return imageService.getImages(productDetailsId);
    }
    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId){
        return imageService.getImage(imageId);
    }
}
