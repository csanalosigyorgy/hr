package hu.webuni.hr.gyuri96.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntityType {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	private String name;

}