package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqEmployee;
import az.orient.eshop.dto.response.RespEmployee;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface EmployeeService {
    Response<RespEmployee> addEmployee(ReqEmployee reqEmployee);

    Response<List<RespEmployee>> getEmployeeList();

    Response<RespEmployee> getEmployeeById(Long id);

    Response<RespEmployee> updateEmployee(ReqEmployee reqEmployee);

    Response deleteEmployee(Long id);
}
