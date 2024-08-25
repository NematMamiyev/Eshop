package az.orient.eshopspring2.controller;

import az.orient.eshopspring2.dto.request.ReqColor;
import az.orient.eshopspring2.dto.response.RespColor;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/color")
public class ColorController {
    private final ColorService colorService;

    @PostMapping("/create")
    public Response<RespColor> addColor(@RequestBody ReqColor reqColor){
        return colorService.addColor(reqColor);
    }

    @GetMapping("/list")
    public Response<List<RespColor>> colorList(){
        return colorService.colorList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespColor> getColorById(@PathVariable Long id){
        return colorService.getColorById(id);
    }

    @PutMapping("/update")
    public Response<RespColor> updateColor(@RequestBody ReqColor reqColor){
        return colorService.updateColor(reqColor);
    }

    @PutMapping("/delete/{id}")
    public Response deleteColor(@PathVariable Long id){
        return colorService.deleteColor(id);
    }
}
