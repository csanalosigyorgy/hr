package hu.webuni.hr.gyuri96.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.gyuri96.dto.EmployeeDto;
import hu.webuni.hr.gyuri96.model.Employee;

@Mapper(componentModel = "spring", uses = CompanyMapper.class)
public interface EmployeeMapper {

	@Named("toEmployeeDto")
	@Mapping(target = "company.employees", ignore = true)
	@Mapping(target = "entryDate", source = "dateOfEntry")
	@Mapping(target = "rank", source = "jobTitle")
	EmployeeDto toEmployeeDto(Employee employee);

	@IterableMapping(qualifiedByName = "toEmployeeDto")
	List<EmployeeDto> toEmployeeDtos(List<Employee> employee);

	@Named("toEmployeeDtoIgnoreCompany")
	@Mapping(target = "company", ignore = true)
	@Mapping(target = "entryDate", source = "dateOfEntry")
	@Mapping(target = "rank", source = "jobTitle")
	EmployeeDto toEmployeeDtoIgnoreCompany(Employee employee);

	@Named(value = "toEmployeeDtosIgnoreCompany")
	@IterableMapping(qualifiedByName = "toEmployeeDtoIgnoreCompany")
	List<EmployeeDto> toEmployeeDtosIgnoreCompany(List<Employee> employee);


	@Named("toEmployee")
	@InheritInverseConfiguration(name = "toEmployeeDto")
	Employee toEmployee(EmployeeDto employeeDto);

	@IterableMapping(qualifiedByName = "toEmployee")
	List<Employee> toEmployees(List<EmployeeDto> employeesDto);
}
