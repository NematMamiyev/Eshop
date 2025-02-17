package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqColor;
import az.orient.eshop.dto.response.RespColor;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.ColorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/colors")
public class ColorController {
    private final ColorService colorService;

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PostMapping
    public Response<RespColor> addColor(@RequestBody @Valid ReqColor reqColor){
        return colorService.addColor(reqColor);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<List<RespColor>> colorList(){
        return colorService.colorList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Response<RespColor> getColorById(@PathVariable @NotNull(message = "Id is required") Long id){
        return colorService.getColorById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Response<RespColor> updateColor(@PathVariable @NotNull(message = "Id is required") Long id,@RequestBody @Valid ReqColor reqColor){
        return colorService.updateColor(id, reqColor);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public RespStatus deleteColor(@PathVariable @NotNull(message = "Id is required") Long id){
        return colorService.deleteColor(id);
    }
}
