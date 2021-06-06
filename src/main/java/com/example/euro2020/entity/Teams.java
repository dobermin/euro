package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "teams", uniqueConstraints = {@UniqueConstraint(columnNames = "teams")})
public class Teams extends MyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name = "teams")
	private String teams;
	@Column(name = "img")
	private String img;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "party_id")
	private Party party;

	@OneToMany (mappedBy="teams")
	private List<Player> players;

	@OneToMany(mappedBy = "champion")
	private List<Users> usr;

	@OneToMany(mappedBy = "teams")
	private List<PlacingTeam> placings;

	@OneToOne(mappedBy = "team", fetch=FetchType.EAGER)
	private Standings standing;

	@OneToMany(mappedBy = "next")
	private List<Matches> matches;

	@OneToMany (mappedBy="next", fetch=FetchType.LAZY)
	private List<Prognosis> nextPrognosis;

	@OneToMany (mappedBy="usr", fetch=FetchType.LAZY)
	private List<Next> next;

	@Override
	public String toString () {
		return "Teams{" +
			"id=" + id +
			", teams='" + teams + '\'' +
			", party=" + party +
			'}';
	}
}