package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqEmployee;
import az.orient.eshop.dto.response.RespEmployee;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface EmployeeService {
    Response<RespEmployee> addEmployee(ReqEmployee reqEmployee, HttpServletRequest httpServletRequest);

    Response<List<RespEmployee>> getEmployeeList();

    Response<RespEmployee> getEmployeeById(Long id);

    Response<RespEmployee> updateEmployee(Long id, ReqEmployee reqEmployee);

    RespStatus deleteEmployee(Long id);
}
