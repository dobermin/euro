package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "placing", uniqueConstraints = {@UniqueConstraint(columnNames = {"team", "usr"})})
public class Placing extends MyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "position")
	private int position;

	@ManyToOne
	@JoinColumn(name = "team", referencedColumnName = "id")
	@NotNull
	private Teams team;

	@ManyToOne
	@JoinColumn(name = "usr", referencedColumnName = "id")
	@NotNull
	private Users user;

	@Override
	public String toString () {
		return "Placing{}";
	}
}
