package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqColor;
import az.orient.eshop.dto.response.RespColor;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/colors")
public class ColorController {
    private final ColorService colorService;

    @PostMapping
    public Response<RespColor> addColor(@RequestBody ReqColor reqColor){
        return colorService.addColor(reqColor);
    }

    @GetMapping
    public Response<List<RespColor>> colorList(){
        return colorService.colorList();
    }

    @GetMapping("/{id}")
    public Response<RespColor> getColorById(@PathVariable Long id){
        return colorService.getColorById(id);
    }

    @PutMapping
    public Response<RespColor> updateColor(@RequestBody ReqColor reqColor){
        return colorService.updateColor(reqColor);
    }

    @DeleteMapping("/{id}")
    public Response deleteColor(@PathVariable Long id){
        return colorService.deleteColor(id);
    }
}
