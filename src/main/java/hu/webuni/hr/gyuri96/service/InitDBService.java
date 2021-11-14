package hu.webuni.hr.gyuri96.service;

import static hu.webuni.hr.gyuri96.model.RequiredEducationLevel.HIGH_SCHOOL;
import static hu.webuni.hr.gyuri96.model.RequiredEducationLevel.UNIVERSITY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gyuri96.model.Company;
import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.model.LegalEntityType;
import hu.webuni.hr.gyuri96.model.Position;
import hu.webuni.hr.gyuri96.model.PositionDetailsByCompany;
import hu.webuni.hr.gyuri96.repository.CompanyRepository;
import hu.webuni.hr.gyuri96.repository.EmployeeRepository;
import hu.webuni.hr.gyuri96.repository.LegalEntityTypeRepository;
import hu.webuni.hr.gyuri96.repository.PositionDetailsByCompanyRepository;
import hu.webuni.hr.gyuri96.repository.PositionRepository;

@Service
public class InitDBService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PositionRepository positonRepository;

	@Autowired
	private LegalEntityTypeRepository legalEntityTypeRepository;

	@Autowired
	private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;

	public void clearDB(){
		employeeRepository.deleteAll();
		companyRepository.deleteAll();
	}

	@Transactional
	public void insertTestData(){

		LegalEntityType kft = new LegalEntityType(1, "Kft.");
		legalEntityTypeRepository.save(kft);

		List<Position> torleyPositions = new ArrayList<>();
		Position torleyFactoryWorker = new Position(0L, "Factory worker", HIGH_SCHOOL, null);
		torleyPositions.add(torleyFactoryWorker);
		Position torleyShiftManager = new Position(0L, "Shift Manager", UNIVERSITY, null);
		torleyPositions.add(torleyShiftManager);
		positonRepository.saveAll(torleyPositions);

		Company torley = new Company(0L, "01-09-883786", "Törley Pezsgőpincészet Kft.", "1222 Budapest, Háros u. 2-6.", kft, null);
		companyRepository.save(torley);

		torley.addEmployee(new Employee(0L, "Fehér Péter", 3420, LocalDate.of(2012, 5, 19), torleyShiftManager, torley));
		torley.addEmployee(new Employee(0L, "Vona Mária",  1890, LocalDate.of(2016, 7, 18), torleyFactoryWorker, torley));
		torley.addEmployee(new Employee(0L, "Varga Tamás",  1280, LocalDate.of(2018, 4, 2), torleyFactoryWorker, torley));
		torley.addEmployee(new Employee(0L, "Tóth Lívia", 1650, LocalDate.of(2016, 5, 4), torleyFactoryWorker, torley));
		torley.addEmployee(new Employee(0L, "Érdi Géza",  1760, LocalDate.of(2017, 9, 11), torleyFactoryWorker, torley));
		employeeRepository.saveAll(torley.getEmployees());

		positionDetailsByCompanyRepository.save(new PositionDetailsByCompany(0L, 1250, torley, torleyFactoryWorker));
		positionDetailsByCompanyRepository.save(new PositionDetailsByCompany(0L, 3000, torley, torleyShiftManager));


//		Company naplemente = new Company(0L, "01-09-562739", "Naplemente Borászati Kft.", null, "8253 Révfülöp, Szilva utca 4.", null, null);
//		naplemente.addEmployee(new Employee(0L, "Vasutas Nóra", "Team Leader", 2460, LocalDate.of(2020, 10, 5), companyRepository.getById(2L)));
//		naplemente.addEmployee(new Employee(0L, "Vörös Eszter", "Employee", 1380, LocalDate.of(2013, 9, 16), companyRepository.getById(2L)));
//		naplemente.addEmployee(new Employee(0L, "Varga János", "Employee", 1450, LocalDate.of(2016, 7, 18), companyRepository.getById(2L)));
//		companies.add(naplemente);
//

	}
}
