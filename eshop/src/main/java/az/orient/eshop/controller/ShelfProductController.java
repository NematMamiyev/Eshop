package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqShelfProduct;
import az.orient.eshop.dto.response.RespShelfProduct;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ShelfProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shelfproducts")
public class ShelfProductController {
    private final ShelfProductService shelfProductService;

    @PostMapping
    public Response<RespShelfProduct> addProductInShelf(@RequestBody ReqShelfProduct reqShelfProduct){
        return shelfProductService.addProductInShelf(reqShelfProduct);
    }

    @DeleteMapping
    public Response<RespShelfProduct> deleteProductInShelf(@RequestBody ReqShelfProduct reqShelfProduct){
        return shelfProductService.deleteProductInShelf(reqShelfProduct);
    }
}
