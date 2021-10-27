package hu.webuni.hr.gyuri96.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.gyuri96.dto.EmployeeDto;
import hu.webuni.hr.gyuri96.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	@Mapping(target = "entryDate", source = "dateOfEntry")
	@Mapping(target = "rank", source = "jobTitle")
	EmployeeDto toDto(Employee employee);

	@InheritInverseConfiguration(name = "toDto")
	Employee toEmployee(EmployeeDto employeeDto);

	List<EmployeeDto> toDtos(List<Employee> employees);

	List<Employee> toEmployees(List<EmployeeDto> employeesDto);
}
