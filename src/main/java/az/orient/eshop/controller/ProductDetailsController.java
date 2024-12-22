package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ProductDetailsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productdetails")
@RequiredArgsConstructor
public class ProductDetailsController {

    private final ProductDetailsService productDetailsService;

    @PostMapping
    public Response<RespProductDetails> addProductDetails( @RequestBody @Valid ReqProductDetails reqProductDetails){
        return productDetailsService.addProductDetails(reqProductDetails);
    }

    @PutMapping("/{id}")
    public Response<RespProductDetails> updateProductDetails(@PathVariable @NotNull(message = "Id is required") Long id, @RequestBody @Valid ReqProductDetails reqProductDetails){
        return productDetailsService.updateProductDetails(id,reqProductDetails);
    }

    @GetMapping
    public Response<List<RespProductDetails>> getProductDetails(){
        return productDetailsService.getProductDetails();
    }

    @GetMapping("/{id}")
    public Response<RespProductDetails> getProductDetailsById(@PathVariable @NotNull(message = "Id is required") Long id){
        return productDetailsService.getProductDetailsById(id);
    }

    @DeleteMapping("/{id}")
    public RespStatus deleteProductDetails(@PathVariable @NotNull(message = "Id is required") Long id){
        return productDetailsService.deleteProductDetails(id);
    }

}
