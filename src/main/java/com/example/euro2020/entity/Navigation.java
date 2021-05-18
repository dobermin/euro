package com.example.euro2020.entity;

import com.example.euro2020.security.model.enums.Roles;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "navigation")
public class Navigation extends MyEntity {

	@Id
	private Long id;
	@Column(name = "link")
	private String link;
	@Column(name = "title")
	private String title;
	@Column(name = "section")
	private String section;
	@Column(name = "usr")
	@Enumerated(EnumType.STRING)
	private Roles user;
	@Column(name = "position")
	private Integer position;

	public Navigation (String title, String link) {
		this.title = title;
		this.link = link;
	}

	public Navigation () {
	}
}
