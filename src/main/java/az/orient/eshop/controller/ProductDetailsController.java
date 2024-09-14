/*
package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productdetails")
public class ProductDetailsController {
    private final ProductDetailsService productDetailsService;

    @PostMapping("/add")
    public Response<List<RespProductDetails>> addProductDetails(@RequestBody List<ReqProductDetails> reqProductDetailsList ) {
        return productDetailsService.addProductDetails(reqProductDetailsList);
    }

    @GetMapping("/list")
    public Response<List<RespProductDetails>> getProductDetailsList() {
        return productDetailsService.getProductDetailsList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespProductDetails> getProductDetailsById(@PathVariable Long id) {
        return productDetailsService.getProductDetailsById(id);
    }

    @PutMapping("/update")
    public Response<List<RespProductDetails>> updateProductDetails(@RequestBody List<ReqProductDetails> reqProductDetailsList ) {
        return productDetailsService.updateProductDetails(reqProductDetailsList);
    }

    @PutMapping("/delete/{id}")
    public Response deleteProductDetails(@PathVariable Long id) {
        return productDetailsService.deleteProductDetails(id);
    }
}
*/
