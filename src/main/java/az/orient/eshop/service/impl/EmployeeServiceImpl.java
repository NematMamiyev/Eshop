package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqEmployee;
import az.orient.eshop.dto.response.RespEmployee;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Employee;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Position;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.EmployeeRepository;
import az.orient.eshop.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Response<RespEmployee> addEmployee(ReqEmployee reqEmployee) {
        Response<RespEmployee> response = new Response<>();
        try {
            String name = reqEmployee.getName();
            String surname = reqEmployee.getSurname();
            String email = reqEmployee.getEmail();
            String phone = reqEmployee.getPhone();
            String password = reqEmployee.getPassword();
            Position.fromValue(reqEmployee.getPosition().getValue());
            if (name == null || surname == null || email == null || phone == null || password == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            boolean uniqueEmail = employeeRepository.existsEmployeeByEmailIgnoreCaseAndActive(email, EnumAvailableStatus.ACTIVE.getValue());
            boolean uniquePhone = employeeRepository.existsEmployeeByPhoneIgnoreCaseAndActive(phone, EnumAvailableStatus.ACTIVE.getValue());
            if (uniquePhone || uniqueEmail) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Email or phone number available in the database");
            }
            Employee employee = Employee.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .phone(phone)
                    .password(password)
                    .position(reqEmployee.getPosition())
                    .build();
            employeeRepository.save(employee);
            RespEmployee respEmployee = convert(employee);
            response.setT(respEmployee);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<List<RespEmployee>> getEmployeeList() {
        Response<List<RespEmployee>> response = new Response<>();
        try {
            List<Employee> employeeList = employeeRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (employeeList.isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Customer list empty");
            }
            List<RespEmployee> respEmployeeList = employeeList.stream().map(this::convert).toList();
            response.setT(respEmployeeList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespEmployee> getEmployeeById(Long id) {
        Response<RespEmployee> response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
            }
            Employee employee = employeeRepository.findEmployeeByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (employee == null) {
                throw new EshopException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
            }
            RespEmployee respEmployee = convert(employee);
            response.setT(respEmployee);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespEmployee> updateEmployee(ReqEmployee reqEmployee) {
        Response<RespEmployee> response = new Response<>();
        try {
            Long id = reqEmployee.getId();
            String name = reqEmployee.getName();
            String surname = reqEmployee.getSurname();
            String email = reqEmployee.getEmail();
            String phone = reqEmployee.getPhone();
            String password = reqEmployee.getPassword();
            Position.fromValue(reqEmployee.getPosition().getValue());
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
            employee.setName(name);
            employee.setSurname(surname);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setPassword(password);
            employee.setPosition(reqEmployee.getPosition());
            employeeRepository.save(employee);
            RespEmployee respEmployee = convert(employee);
            response.setT(respEmployee);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response deleteEmployee(Long id) {
        Response response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
            }
            Employee employee = employeeRepository.findEmployeeByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (employee == null){
                throw new EshopException(ExceptionConstants.EMPLOYEE_NOT_FOUND, "Employee not found");
            }
            employee.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            employeeRepository.save(employee);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    private RespEmployee convert(Employee employee) {
        return RespEmployee.builder()
                .id(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .password(employee.getPassword())
                .position(employee.getPosition())
                .build();
    }
}
