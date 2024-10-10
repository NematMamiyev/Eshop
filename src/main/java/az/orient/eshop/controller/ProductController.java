package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqProduct;
import az.orient.eshop.dto.response.RespProduct;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.entity.ProductImage;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.ProductImageRepository;
import az.orient.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductImageRepository productImageRepository;
    private final ProductDetailsRepository productDetailsRepository;

    @PostMapping
    public Response<RespProduct> addProduct(@RequestBody ReqProduct reqProduct){
        return productService.addProduct(reqProduct);
    }

    @GetMapping
    public Response<List<RespProduct>> getProductList(){
        return productService.getProductList();
    }

    @GetMapping("/{id}")
    public Response<RespProduct> getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping
    public Response<RespProduct> updateProduct(@RequestBody ReqProduct reqProduct){
        return productService.updateProduct(reqProduct);
    }

    @DeleteMapping("/{id}")
    public Response deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }


    @PostMapping("/image/{id}")
    public Response addImages(@RequestParam("file") Set<MultipartFile> files, @PathVariable Long id) throws Exception {
        Response response = new Response<>();

        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Fayl adı düzgünlüyünü yoxlamaq
            if (fileName.contains("..")) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Filename contains invalid path sequence");
            }

            // Məhsul detalları tapılır
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(id, 1);

            // Fayl məlumatları ilə ProductImage yaradılması və saxlanılması
            ProductImage productImage = new ProductImage(fileName, file.getContentType(), file.getBytes(), productDetails);
            productImageRepository.save(productImage);
        }

        return response;
    }
}
