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
@RequestMapping("subcategory")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    @PostMapping("/create")
    public Response<RespSubcategory> addSubcategory(@RequestBody ReqSubcategory reqSubcategory){
        return subcategoryService.addSubcategory(reqSubcategory);
    }

    @GetMapping("/list")
    public Response<List<RespSubcategory>> getSubcategoryList(){
        return subcategoryService.getSubcategoryList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespSubcategory> getSubcategoryById(@PathVariable Long id){
        return subcategoryService.getSubcategoryById(id);
    }

    @PutMapping("/update")
    public Response<RespSubcategory> updateSubcategory(@RequestBody ReqSubcategory reqSubcategory){
        return subcategoryService.updateSubcategory(reqSubcategory);
    }

    @PutMapping("/delete/{id}")
    public Response deleteSubcategory(@PathVariable Long id){
        return subcategoryService.deleteSubcategory(id);
    }
}
