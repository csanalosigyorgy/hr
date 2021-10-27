package hu.webuni.hr.gyuri96.mapper;

import java.util.List;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


import hu.webuni.hr.gyuri96.dto.CompanyDto;
import hu.webuni.hr.gyuri96.model.Company;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface CompanyMapper {

	Company toCompany(CompanyDto companyDto);


	//@Named("detialedCompany")
	CompanyDto toDto(Company company);

	//@InheritConfiguration(name = "toDto")
	List<CompanyDto> toDtos(List<Company> company);


	@Named("withoutEmployees")
	@Mapping(target = "CompanyDto.employees", ignore = true)
	CompanyDto toDtoWithoutEmployees(Company company);

	// TODO itt nem a megfelelő metódust használja a Mappeléshez!
	@Mapping(target = "employees", qualifiedByName = "withoutEmployees")
	List<CompanyDto> toDtosWithoutEmployees(List<Company> companies);

}
