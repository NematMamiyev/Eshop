package az.orient.eshopspring2.controller;

import az.orient.eshopspring2.dto.request.ReqSubcategory;
import az.orient.eshopspring2.dto.response.RespSubcategory;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.service.SubcategoryService;
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
