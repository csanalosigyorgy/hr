package hu.webuni.hr.gyuri96.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.gyuri96.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {

	@EntityGraph("Position.full")
	List<Position>findAll();

	@EntityGraph("Position.full")
	Optional<Position>findById(Long id);

	@EntityGraph("Position.full")
	List<Position> findByName(String name);
}
