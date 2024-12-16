package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqShelfProductDetails;
import az.orient.eshop.dto.response.RespShelfProductDetails;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ShelfProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shelfproducts")
public class ShelfProductDetailsController {

    private final ShelfProductDetailsService shelfProductDetailsService;

    @PostMapping
    public Response<RespShelfProductDetails> addProductInShelf(@RequestBody ReqShelfProductDetails reqShelfProduct){
        return shelfProductDetailsService.addProductInShelf(reqShelfProduct);
    }

    @DeleteMapping
    public Response deleteProductInShelf(@RequestBody ReqShelfProductDetails reqShelfProduct){
        return shelfProductDetailsService.deleteProductInShelf(reqShelfProduct);
    }
}
