package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqEmployee;
import az.orient.eshop.dto.response.RespEmployee;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @PostMapping
    public Response<RespEmployee> addEmployee(@RequestBody @Valid ReqEmployee reqEmployee, HttpServletRequest httpServletRequest){
        return employeeService.addEmployee(reqEmployee, httpServletRequest);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @GetMapping
    public Response<List<RespEmployee>> getEmployeeList(){
        return employeeService.getEmployeeList();
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public Response<RespEmployee> getEmployeeById(@PathVariable @NotNull(message = "Id is required") Long id){
        return employeeService.getEmployeeById(id);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Response<RespEmployee> updateEmployee(@PathVariable @NotNull(message = "Id is required") Long id, @RequestBody @Valid ReqEmployee reqEmployee){
        return employeeService.updateEmployee(id,reqEmployee);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public RespStatus deleteEmployee(@PathVariable @NotNull(message = "Id is required") Long id){
        return employeeService.deleteEmployee(id);
    }
}
