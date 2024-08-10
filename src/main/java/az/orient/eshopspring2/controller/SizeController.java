package az.orient.eshopspring2.controller;

import az.orient.eshopspring2.dto.request.ReqSize;
import az.orient.eshopspring2.dto.response.RespSize;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/size")
public class SizeController {

    private final SizeService sizeService;

    @PostMapping("/create")
    public Response<RespSize> addSize(@RequestBody ReqSize reqSize){
        return sizeService.addSize(reqSize);
    }

    @GetMapping("/list")
    public Response<List<RespSize>> getSizeList(){
        return sizeService.getSizeList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespSize> getSizeById(@PathVariable Long id){
        return sizeService.getSizeById(id);
    }

    @PutMapping("/update")
    public Response<RespSize> updateSize(@RequestBody ReqSize reqSize){
        return sizeService.updateSize(reqSize);
    }

    @PutMapping("/delete/{id}")
    public Response deleteSize(@PathVariable Long id){
        return sizeService.deleteSize(id);
    }
}
