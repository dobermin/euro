package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "prognosis", uniqueConstraints = @UniqueConstraint(columnNames = {"matches", "usrs"}))
public class Prognosis extends MyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name = "home")
	private Integer home;
	@Column(name = "away")
	private Integer away;
	@Column(name = "points")
	private Integer points;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "matches", referencedColumnName = "id")
	private Matches match;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usrs", referencedColumnName = "id")
	private Users usr;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "next", referencedColumnName = "id")
	private Teams next;

}
