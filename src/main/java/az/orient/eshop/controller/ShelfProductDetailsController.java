package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqShelfProductDetails;
import az.orient.eshop.dto.response.RespShelfProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ShelfProductDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shelfproducts")
public class ShelfProductDetailsController {

    private final ShelfProductDetailsService shelfProductDetailsService;

    @PostMapping
    public Response<RespShelfProductDetails> addProductInShelf(@RequestBody @Valid ReqShelfProductDetails reqShelfProductDetails){
        return shelfProductDetailsService.addProductInShelf(reqShelfProductDetails);
    }

    @DeleteMapping
    public RespStatus deleteProductInShelf(@RequestBody @Valid ReqShelfProductDetails reqShelfProductDetails){
        return shelfProductDetailsService.deleteProductInShelf(reqShelfProductDetails);
    }
}
