package az.orient.eshopspring2.controller;

import az.orient.eshopspring2.dto.request.ReqProduct;
import az.orient.eshopspring2.dto.response.RespProduct;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public Response<RespProduct> addProduct(@RequestBody ReqProduct reqProduct){
        return productService.addProduct(reqProduct);
    }

    @GetMapping("/list")
    public Response<List<RespProduct>> getProductList(){
        return productService.getProductList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespProduct> getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping("/update")
    public Response<RespProduct> updateProduct(@RequestBody ReqProduct reqProduct){
        return productService.updateProduct(reqProduct);
    }

    @PutMapping("/delete/{id}")
    public Response deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }
}
