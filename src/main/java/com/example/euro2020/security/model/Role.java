package com.example.euro2020.security.model;

import com.example.euro2020.security.model.enums.Roles;

import javax.persistence.*;

@Entity
//@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, name = "name")
	private Roles name;

	public Role() {}

	public Role(Roles name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Roles getName() {
		return name;
	}

	public void setName(Roles name) {
		this.name = name;
	}

}
