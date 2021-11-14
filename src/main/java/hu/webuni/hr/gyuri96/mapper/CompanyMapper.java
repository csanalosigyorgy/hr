package hu.webuni.hr.gyuri96.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.gyuri96.dto.CompanyDto;
import hu.webuni.hr.gyuri96.model.Company;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface CompanyMapper {

	@Named("toCompanyDto")
	@Mapping(target = "employees", qualifiedByName = "toEmployeeDtoIgnoreCompany")
	CompanyDto toCompanyDto(Company company);

	@IterableMapping(qualifiedByName = "toCompanyDto")
	List<CompanyDto> toCompanyDtos(List<Company> company);

	@Named("toCompanyDtoIgnoreEmployees")
	@Mapping(target = "employees", ignore = true)
	CompanyDto toCompanyDtoIgnoreEmployees(Company company);

	@IterableMapping(qualifiedByName = "toCompanyDtoIgnoreEmployees")
	List<CompanyDto> toCompanyDtosIgnoreEmployees(List<Company> company);

	Company toCompany(CompanyDto companyDto);

}
