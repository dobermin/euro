package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "placing_team", uniqueConstraints = {@UniqueConstraint(columnNames = {"team", "usrs"})})
public class PlacingTeam extends MyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "position")
	private int position;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "team")
	private Teams teams;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usrs", referencedColumnName = "id")
	private Users usr;
}
