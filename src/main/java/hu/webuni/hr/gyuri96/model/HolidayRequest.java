package hu.webuni.hr.gyuri96.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NamedEntityGraph(name = "HolidayRequest.full",
			attributeNodes = { @NamedAttributeNode( value = "issuer", subgraph = "subgraph.issuer"),
			},
			subgraphs = { @NamedSubgraph(
							name = "subgraph.issuer",
							attributeNodes = {@NamedAttributeNode(value = "position")}
			)}
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayRequest {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Employee issuer;

	private LocalDateTime issuingDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Employee approver;

	private LocalDateTime approvingTime;

	private LocalDate startingDate;

	private LocalDate endingDate;

	private Boolean isApproved;
}
