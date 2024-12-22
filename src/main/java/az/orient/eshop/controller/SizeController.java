package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqSize;
import az.orient.eshop.dto.response.RespSize;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.SizeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/size")
public class SizeController {

    private final SizeService sizeService;

    @PostMapping
    public Response<RespSize> addSize(@RequestBody @Valid ReqSize reqSize){
        return sizeService.addSize(reqSize);
    }

    @GetMapping
    public Response<List<RespSize>> getSizeList(){
        return sizeService.getSizeList();
    }

    @GetMapping("/{id}")
    public Response<RespSize> getSizeById(@PathVariable @NotNull(message = "Id is required") Long id){
        return sizeService.getSizeById(id);
    }

    @PutMapping("/{id}")
    public Response<RespSize> updateSize(@PathVariable @NotNull(message = "Id is required") Long id,@RequestBody @Valid ReqSize reqSize){
        return sizeService.updateSize(id, reqSize);
    }

    @DeleteMapping("/{id}")
    public RespStatus deleteSize(@PathVariable @NotNull(message = "Id is required") Long id){
        return sizeService.deleteSize(id);
    }
}
