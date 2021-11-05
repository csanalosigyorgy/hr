package hu.webuni.hr.gyuri96.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gyuri96.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
