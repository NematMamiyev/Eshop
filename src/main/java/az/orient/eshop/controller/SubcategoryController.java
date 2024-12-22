package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqSubcategory;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.RespSubcategory;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.SubcategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("subcategorys")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    @PostMapping
    public Response<RespSubcategory> addSubcategory(@RequestBody @Valid ReqSubcategory reqSubcategory){
        return subcategoryService.addSubcategory(reqSubcategory);
    }

    @GetMapping
    public Response<List<RespSubcategory>> getSubcategoryList(){
        return subcategoryService.getSubcategoryList();
    }

    @GetMapping("/{id}")
    public Response<RespSubcategory> getSubcategoryById(@PathVariable @NotNull(message = "Id is required") Long id){
        return subcategoryService.getSubcategoryById(id);
    }

    @PutMapping("/{id}")
    public Response<RespSubcategory> updateSubcategory(@PathVariable @NotNull(message = "Id is required") Long id,@RequestBody @Valid ReqSubcategory reqSubcategory){
        return subcategoryService.updateSubcategory(id,reqSubcategory);
    }

    @DeleteMapping("/{id}")
    public RespStatus deleteSubcategory(@PathVariable @NotNull(message = "Id is required") Long id){
        return subcategoryService.deleteSubcategory(id);
    }
}
