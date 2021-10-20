package hu.webuni.hr.gyuri96.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import hu.webuni.hr.gyuri96.dto.EmployeeDTO;
import hu.webuni.hr.gyuri96.model.Employee;

@Controller
public class EmployeeTLController {

	List<EmployeeDTO> allEmployees = new ArrayList<>();

	{
		allEmployees.add(new EmployeeDTO(1, "Kiss János", "employee", 1000, LocalDateTime.of(2020, 10, 5, 0, 0)));
		allEmployees.add(new EmployeeDTO(2, "Varga Piroska", "team leader", 1625, LocalDateTime.of(2016, 4, 18, 0, 0)));
		allEmployees.add(new EmployeeDTO(3, "Fekete Márk", "employee", 1150, LocalDateTime.of(2019, 12, 2, 0, 0)));
	}

	@GetMapping("/employees")
	public String getAll(Map<String, Object> model) {
		model.put("employeeList", allEmployees);
		model.put("newEmployee", new Employee());
		return "employees";
	}

	@PostMapping("/employees")
	public String addEmployee(EmployeeDTO employee) {
		allEmployees.add(employee);
		return "redirect:employees";
	}

	@PutMapping("/modify")
	public String modifyEmployee(Map<String, Object> model, EmployeeDTO modifiedEmployee){
		model.put("modifiedEmployee", modifiedEmployee);
		return "modify";
	}

	@GetMapping("/employees/{employee}")
	public String deleteEmployee(@PathVariable EmployeeDTO employee) {
		allEmployees.remove(employee);
		return "redirect:employees";
	}
}
