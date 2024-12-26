package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqCategory;
import az.orient.eshop.dto.response.RespCategory;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PostMapping
    public Response<RespCategory> addCategory(@RequestBody @Valid ReqCategory reqCategory) {
        return categoryService.addCategory(reqCategory);
    }

    @GetMapping
    public Response<List<RespCategory>> categoryList() {
        return categoryService.categoryList();
    }

    @GetMapping("/{id}")
    public Response<RespCategory> getCategoryById(@PathVariable @NotNull(message = "Id is required") Long id) {
        return categoryService.getCategoryById(id);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Response<RespCategory> updateCategory(@PathVariable @NotNull(message = "Id is required") Long id, @RequestBody @Valid ReqCategory reqCategory) {
        return categoryService.updateCategory(id, reqCategory);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public RespStatus deleteCategory(@PathVariable @NotNull(message = "Id is required") Long id) {
        return categoryService.deleteCategory(id);
    }
}
