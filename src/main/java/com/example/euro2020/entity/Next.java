package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;

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
	@JoinColumn(name = "teams", referencedColumnName = "id")
	private Teams teams;

	@ManyToOne
	@JoinColumn(name = "usr", referencedColumnName = "id")
	private Users usr;

	@ManyToOne
	@JoinColumn(name = "tour", referencedColumnName = "id")
	private Tour tour;

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public Tour getTour () {
		return tour;
	}

	public void setTour (Tour tour) {
		this.tour = tour;
	}
}