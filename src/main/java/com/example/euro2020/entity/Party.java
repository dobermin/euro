package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "party", uniqueConstraints = @UniqueConstraint(columnNames = {"gro"}))
public class Party extends MyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name = "gro")
	@NotEmpty
	private String party;

	@OneToMany (mappedBy="party", fetch = FetchType.LAZY)
	private List<Teams> teams;

	@OneToOne
	private Standings standings;

	public List<Teams> getTeams () {
		return teams;
	}

	public void setTeams (List<Teams> teams) {
		this.teams = teams;
	}

	public Standings getStandings () {
		return standings;
	}

	public void setStandings (Standings standings) {
		this.standings = standings;
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public String getParty () {
		return party;
	}

	public void setParty (String party) {
		this.party = party;
	}
}