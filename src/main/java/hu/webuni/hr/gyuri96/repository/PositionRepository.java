package hu.webuni.hr.gyuri96.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gyuri96.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {

	List<Position> findByName(String name);
}
