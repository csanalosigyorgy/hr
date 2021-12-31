package hu.webuni.hr.gyuri96.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.gyuri96.model.Employee;
import hu.webuni.hr.gyuri96.model.Position;
import hu.webuni.hr.gyuri96.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;

	public void setPositionForEmployee(Employee employee){
		Position position = employee.getPosition();
		if(Objects.nonNull(position)){
			String positionName = position.getName();
			if(Objects.isNull(position.getId()) && Objects.nonNull(positionName)){
				List<Position> positionsByName = positionRepository.findByName(positionName);
				if(positionsByName.isEmpty()){
					position = positionRepository.save(new Position(positionName, null));
				} else {
					position = positionsByName.get(0);
				}
			}
			position.addEmployee(employee);
		}
		//employee.setPosition(position);
	}

}
