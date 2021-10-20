package hu.webuni.hr.gyuri96.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.gyuri96.dto.EmployeeDTO;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


	Map<Long, EmployeeDTO> employees = new HashMap<>();

	{
		employees.put(1L, new EmployeeDTO(1, "Kiss János", "employee", 1000, LocalDateTime.of(2020, 10, 5, 0, 0)));
		employees.put(2L, new EmployeeDTO(2, "Varga Piroska", "team leader", 1625, LocalDateTime.of(2016, 4, 18, 0, 0)));
		employees.put(3L, new EmployeeDTO(3, "Fekete Márk", "employee", 1150, LocalDateTime.of(2019, 12, 2, 0, 0)));
	}

	@GetMapping
	public List<EmployeeDTO> getEmployees(@RequestParam(required = false) Integer limit,
										  @RequestParam(required = false) String rank) {

		Stream<EmployeeDTO> stream = employees.values().stream();

		if(limit != null){
			stream = stream.filter(e -> e.getSalary() > limit);
		}
		if(rank != null){
			stream = stream.filter(e -> e.getRank().equals(rank));
		}

		return stream.collect(Collectors.toList());

	}

	@GetMapping("/get/{id}")
	public ResponseEntity<EmployeeDTO> getById(@PathVariable Long id) {
		ResponseEntity<EmployeeDTO> resultEntity;
		if (employees.containsKey(id)) {
			resultEntity = ResponseEntity.ok(employees.get(id));
		} else {
			resultEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return resultEntity;
	}

	@PostMapping
	public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employee) {
		ResponseEntity<EmployeeDTO> response;
		if (!employees.containsKey(employee.getId())) {
			employees.put(employee.getId(), employee);
			response = ResponseEntity.ok(employee);
		} else {
			response = ResponseEntity.status(HttpStatus.FOUND).build();
		}
		return response;
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDTO> modifyById(@PathVariable Long id, @RequestBody EmployeeDTO employee) {
		employee.setId(id);
		ResponseEntity<EmployeeDTO> response;
		if (employees.containsKey(id)) {
			employees.put(employee.getId(), employee);
			response = ResponseEntity.ok(employee);
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return response;
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		employees.remove(id);
	}
}
