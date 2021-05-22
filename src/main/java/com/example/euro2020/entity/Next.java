package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "next")
public class Next extends MyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "team", referencedColumnName = "id")
	private Teams team;

	@ManyToOne
	@JoinColumn(name = "usr", referencedColumnName = "id")
	private Users usr;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tour", referencedColumnName = "id")
	private Tour tour;

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public Teams getTeam () {
		return team;
	}

	public void setTeam (Teams team) {
		this.team = team;
	}

	public Tour getTour () {
		return tour;
	}

	public void setTour (Tour tour) {
		this.tour = tour;
	}
}