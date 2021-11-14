package hu.webuni.hr.gyuri96.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.gyuri96.service.SalaryService;

@RestController
@RequestMapping("api/salary")
public class SalaryController {

	@Autowired
	private SalaryService salaryService;

	@PutMapping("{companyId}/raiseMin/{positionName}/{minSalary}")
	public void raiseMinSalary(@PathVariable long companyId, @PathVariable String positionName, @PathVariable int minSalary){
		salaryService.raiseMinSalary(companyId, positionName, minSalary);
	}
}
