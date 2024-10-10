package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqCategory;
import az.orient.eshop.dto.response.RespCategory;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categoryes")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public Response<RespCategory> addCategory(@RequestBody ReqCategory reqCategory) {
        return categoryService.addCategory(reqCategory);
    }

    @GetMapping
    public Response<List<RespCategory>> categoryList(){
        return categoryService.categoryList();
    }

    @GetMapping("/{id}")
    public Response<RespCategory> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    public Response<RespCategory> updateCategory(@RequestBody ReqCategory reqCategory){
        return categoryService.updateCategory(reqCategory);
    }

    @DeleteMapping("/{id}")
    public Response deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
