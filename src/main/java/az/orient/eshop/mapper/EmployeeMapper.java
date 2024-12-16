package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqEmployee;
import az.orient.eshop.dto.response.RespEmployee;
import az.orient.eshop.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(ReqEmployee reqEmployee);
    RespEmployee toRespEmployee(Employee employee);
    List<RespEmployee> toRespEmployeeList(List<Employee> employeeList);
    void updateEmployeeFromReqEmployee(@MappingTarget Employee employee, ReqEmployee reqEmployee);
}
