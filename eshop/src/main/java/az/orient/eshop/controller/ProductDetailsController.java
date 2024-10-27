package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productdetails")
@RequiredArgsConstructor
public class ProductDetailsController {

    private final ProductDetailsService productDetailsService;

    @PostMapping("/{productId}")
    public Response<RespProductDetails> addProductDetails(@PathVariable Long productId, @RequestBody ReqProductDetails reqProductDetails){
        return productDetailsService.addProductDetails(productId,reqProductDetails);
    }

    @PutMapping("/{id}")
    public Response<RespProductDetails> updateProductDetails(@PathVariable Long id, @RequestBody ReqProductDetails reqProductDetails){
        return productDetailsService.updateProductDetails(id,reqProductDetails);
    }

    @GetMapping
    public Response<List<RespProductDetails>> getProductDetails(){
        return productDetailsService.getProductDetails();
    }

    @GetMapping("/{id}")
    public Response<RespProductDetails> getProductDetailsById(@PathVariable Long id){
        return productDetailsService.getProductDetailsById(id);
    }

    @DeleteMapping("/{id}")
    public Response deleteProductDetails(@PathVariable Long id){
        return productDetailsService.deleteProductDetails(id);
    }

}
