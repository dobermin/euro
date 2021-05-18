package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Table(name = "standings", uniqueConstraints = @UniqueConstraint(columnNames = {"team"}))
public class Standings extends MyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name = "position")
	private Integer position;
	@Column(name = "games")
	private Integer games;
	@Column(name = "win")
	private Integer win;
	@Column(name = "draw")
	private Integer draw;
	@Column(name = "loss")
	private Integer loss;
	@Column(name = "goals_scored")
	private Integer goalsScored;
	@Column(name = "goals_missed")
	private Integer goalsMissed;
	@Column(name = "goals_diff")
	private Integer goalsDiff;
	@Column(name = "points")
	private Integer points;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "party_id", referencedColumnName = "id")
	private Party party;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "team")
	private Teams team;
}
