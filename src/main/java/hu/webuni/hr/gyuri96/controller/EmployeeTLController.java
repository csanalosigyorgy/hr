package hu.webuni.hr.gyuri96.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.gyuri96.model.Employee;

@Controller
public class EmployeeTLController {

	List<Employee> allEmployees = new ArrayList<>();

	{
//		allEmployees.add(new Employee(1, "Kiss János", "employee", 1000, LocalDate.of(2020, 10, 5), null));
//		allEmployees.add(new Employee(2, "Varga Piroska", "team leader", 1625, LocalDate.of(2016, 4, 18), null) );
//		allEmployees.add(new Employee(3, "Fekete Márk", "employee", 1150, LocalDate.of(2019, 12, 2), null));
	}

	@GetMapping("/employees")
	public String getAll(Map<String, Object> model) {
		model.put("employeeList", allEmployees);
		model.put("newEmployee", new Employee());
		return "employees";
	}

	@PostMapping("/employees")
	public String addEmployee(Employee employee) {
		allEmployees.add(employee);
		return "redirect:employees";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable long id){
		allEmployees.removeIf(e -> e.getId() == id);
		return "redirect:/employees";
	}

	@GetMapping("/employees/{id}")
	public String editEmployee(@PathVariable long id, Map<String, Object> model){
		model.put("employee", allEmployees.stream().filter(e-> e.getId() == id).findFirst().get());
		return "editEmployee";
	}

	@PostMapping("/updateEmployee")
	public String updateEmployee(Employee employee){
		for(int i = 0; i < allEmployees.size(); i++){
			if(allEmployees.get(i).getId() == employee.getId()){
				allEmployees.set(i, employee);
				break;
			}
		}
		return "redirect:employees";
	}
}
