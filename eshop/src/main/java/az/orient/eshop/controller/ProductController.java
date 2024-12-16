package az.orient.eshop.controller;


import az.orient.eshop.dto.request.ReqProduct;
import az.orient.eshop.dto.request.ReqProductAndProductDetailsList;
import az.orient.eshop.dto.response.RespProduct;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Response<RespProduct> addProduct(@RequestBody ReqProductAndProductDetailsList reqProductAndProductDetailsList){
        return productService.addProduct(reqProductAndProductDetailsList.getReqProduct(),reqProductAndProductDetailsList.getReqProductDetailsList());
    }

    @GetMapping
    public Response<List<RespProduct>> getProductList(){
        return productService.getProductList();
    }

    @GetMapping("/{id}")
    public Response<RespProduct> getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Response<RespProduct> updateProduct(@PathVariable Long id,@RequestBody ReqProduct reqProduct){
        return productService.updateProduct(id,reqProduct);
    }

    @DeleteMapping("/{id}")
    public Response deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }

}
