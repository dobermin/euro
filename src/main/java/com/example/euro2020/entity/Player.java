package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "players", uniqueConstraints = {@UniqueConstraint(columnNames = "player")})
public class Player extends MyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "position")
	private String position;
	@Column(name = "player")
	private String player;
	@Column(name = "team")
	private String team;
	@Column(name = "display")
	private Boolean display;

	@ManyToOne
	@JoinColumn(name = "country")
	private Teams teams;

	@OneToMany(mappedBy = "bombardier")
	private List<Users> users;

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public String getPosition () {
		return position;
	}

	public void setPosition (String position) {
		this.position = position;
	}

	public String getPlayer () {
		return player;
	}

	public void setPlayer (String player) {
		this.player = player;
	}

	public String getTeam () {
		return team;
	}

	public void setTeam (String team) {
		this.team = team;
	}

	public Boolean getDisplay () {
		return display;
	}

	public void setDisplay (Boolean display) {
		this.display = display;
	}

	public Teams getTeams () {
		return teams;
	}

	public void setTeams (Teams teams) {
		this.teams = teams;
	}

	public List<Users> getUsers () {
		return users;
	}

	public void setUsers (List<Users> users) {
		this.users = users;
	}
}