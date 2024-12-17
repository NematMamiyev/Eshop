package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqEmployee;
import az.orient.eshop.dto.response.RespEmployee;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Employee;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Role;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.EmployeeMapper;
import az.orient.eshop.repository.EmployeeRepository;
import az.orient.eshop.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public Response<RespEmployee> addEmployee(ReqEmployee reqEmployee) {
        Response<RespEmployee> response = new Response<>();
        String name = reqEmployee.getName();
        String surname = reqEmployee.getSurname();
        String email = reqEmployee.getEmail();
        String phone = reqEmployee.getPhone();
        String password = reqEmployee.getPassword();
        Role.fromValue(reqEmployee.getRole().getValue());
        if (name == null || surname == null || email == null || phone == null || password == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
        }
        boolean uniqueEmail = employeeRepository.existsEmployeeByEmailIgnoreCaseAndActive(email, EnumAvailableStatus.ACTIVE.getValue());
        boolean uniquePhone = employeeRepository.existsEmployeeByPhoneIgnoreCaseAndActive(phone, EnumAvailableStatus.ACTIVE.getValue());
        if (uniquePhone || uniqueEmail) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Email or phone number available in the database");
        }
        Employee employee = employeeMapper.toEmployee(reqEmployee);
        employeeRepository.save(employee);
        RespEmployee respEmployee = employeeMapper.toRespEmployee(employee);
        response.setT(respEmployee);
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<List<RespEmployee>> getEmployeeList() {
        Response<List<RespEmployee>> response = new Response<>();
        List<Employee> employeeList = employeeRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
        if (employeeList.isEmpty()) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Customer list empty");
        }
        List<RespEmployee> respEmployeeList = employeeMapper.toRespEmployeeList(employeeList);
        response.setT(respEmployeeList);
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespEmployee> getEmployeeById(Long id) {
        Response<RespEmployee> response = new Response<>();
        if (id == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
        }
        Employee employee = employeeRepository.findEmployeeByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
        if (employee == null) {
            throw new EshopException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
        }
        RespEmployee respEmployee = employeeMapper.toRespEmployee(employee);
        response.setT(respEmployee);
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response<RespEmployee> updateEmployee(Long id, ReqEmployee reqEmployee) {
        Response<RespEmployee> response = new Response<>();
        String name = reqEmployee.getName();
        String surname = reqEmployee.getSurname();
        String email = reqEmployee.getEmail();
        String phone = reqEmployee.getPhone();
        String password = reqEmployee.getPassword();
        Role.fromValue(reqEmployee.getRole().getValue());
        if (id == null || name == null || surname == null || email == null || phone == null || password == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
        }
        Employee employee = employeeRepository.findEmployeeByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
        if (employee == null) {
            throw new EshopException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
        }
        boolean uniqueEmail = employeeRepository.existsEmployeeByEmailIgnoreCaseAndActiveAndIdNot(email, EnumAvailableStatus.ACTIVE.getValue(), id);
        boolean uniquePhone = employeeRepository.existsEmployeeByPhoneIgnoreCaseAndActiveAndIdNot(phone, EnumAvailableStatus.ACTIVE.getValue(), id);
        if (uniqueEmail || uniquePhone) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Email or phone number available in the database");
        }
        employeeMapper.updateEmployeeFromReqEmployee(employee, reqEmployee);
        employeeRepository.save(employee);
        RespEmployee respEmployee = employeeMapper.toRespEmployee(employee);
        response.setT(respEmployee);
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }

    @Override
    public Response deleteEmployee(Long id) {
        Response response = new Response<>();
        if (id == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
        }
        Employee employee = employeeRepository.findEmployeeByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
        if (employee == null) {
            throw new EshopException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
        }
        employee.setActive(EnumAvailableStatus.DEACTIVE.getValue());
        employeeRepository.save(employee);
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }
}
