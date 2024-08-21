package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqEmployee;
import az.orient.eshop.dto.response.RespEmployee;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    public Response<RespEmployee> addEmployee(@RequestBody ReqEmployee reqEmployee){
        return employeeService.addEmployee(reqEmployee);
    }

    @GetMapping("/list")
    public Response<List<RespEmployee>> getEmployeeList(){
        return employeeService.getEmployeeList();
    }

    @GetMapping("/getbyid/{id}")
    public Response<RespEmployee> getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/update")
    public Response<RespEmployee> updateEmployee(@RequestBody ReqEmployee reqEmployee){
        return employeeService.updateEmployee(reqEmployee);
    }

    @PutMapping("/delete/{id}")
    public Response deleteEmployee(@PathVariable Long id){
        return employeeService.deleteEmployee(id);
    }
}
