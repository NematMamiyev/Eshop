package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqEmployee;
import az.orient.eshopspring2.dto.response.RespEmployee;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface EmployeeService {
    Response<RespEmployee> addEmployee(ReqEmployee reqEmployee);

    Response<List<RespEmployee>> getEmployeeList();

    Response<RespEmployee> getEmployeeById(Long id);

    Response<RespEmployee> updateEmployee(ReqEmployee reqEmployee);

    Response deleteEmployee(Long id);
}
