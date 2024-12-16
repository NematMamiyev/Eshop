package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqSubcategory;
import az.orient.eshop.dto.response.RespSubcategory;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("subcategorys")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    @PostMapping
    public Response<RespSubcategory> addSubcategory(@RequestBody ReqSubcategory reqSubcategory){
        return subcategoryService.addSubcategory(reqSubcategory);
    }

    @GetMapping
    public Response<List<RespSubcategory>> getSubcategoryList(){
        return subcategoryService.getSubcategoryList();
    }

    @GetMapping("/{id}")
    public Response<RespSubcategory> getSubcategoryById(@PathVariable Long id){
        return subcategoryService.getSubcategoryById(id);
    }

    @PutMapping("/{id}")
    public Response<RespSubcategory> updateSubcategory(@PathVariable Long id,@RequestBody ReqSubcategory reqSubcategory){
        return subcategoryService.updateSubcategory(id,reqSubcategory);
    }

    @DeleteMapping("/{id}")
    public Response deleteSubcategory(@PathVariable Long id){
        return subcategoryService.deleteSubcategory(id);
    }
}
