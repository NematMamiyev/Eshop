package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqShelf;
import az.orient.eshop.dto.response.RespShelf;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shelfs")
public class ShelfController {
    private final ShelfService shelfService;

    @PostMapping
    public Response<RespShelf> addShelf(@RequestBody ReqShelf reqShelf){
        return shelfService.addShelf(reqShelf);
    }

    @GetMapping
    public Response<List<RespShelf>> shelfList(){
        return shelfService.shelfList();
    }

    @GetMapping("/{id}")
    public Response<RespShelf> getShelfById(@PathVariable Long id){
        return shelfService.getShelfById(id);
    }

    @PutMapping
    public Response<RespShelf> updateShelf(@RequestBody ReqShelf reqShelf){
        return shelfService.updateShelf(reqShelf);
    }

    @DeleteMapping("/{id}")
    public Response deleteShelf(@PathVariable Long id){
        return shelfService.deleteShelf(id);
    }
}
