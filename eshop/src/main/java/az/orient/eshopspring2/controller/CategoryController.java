package az.orient.eshopspring2.controller;

import az.orient.eshopspring2.dto.request.ReqCategory;
import az.orient.eshopspring2.dto.response.RespCategory;
import az.orient.eshopspring2.dto.response.Response;
import az.orient.eshopspring2.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public Response<RespCategory> addCategory(@RequestBody ReqCategory reqCategory) {
        return categoryService.addCategory(reqCategory);
    }

    @GetMapping("/list")
    public Response<List<RespCategory>> categoryList(){
        return categoryService.categoryList();
    }

    @GetMapping("getbyid/{id}")
    public Response<RespCategory> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/update")
    public Response<RespCategory> updateCategory(@RequestBody ReqCategory reqCategory){
        return categoryService.updateCategory(reqCategory);
    }

    @PutMapping("/delete/{id}")
    public Response deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
