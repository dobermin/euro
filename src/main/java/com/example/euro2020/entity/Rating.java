package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "rating", uniqueConstraints = {@UniqueConstraint(columnNames = "usr")})
public class Rating extends MyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "position_before")
	private Integer positionBefore;
	@Column(name = "position_now")
	private Integer positionNow;
	@Column(name = "score")
	private Integer score;
	@Column(name = "all_score")
	private Integer allScore;
	@Column(name = "difference")
	private Integer difference;
	@Column(name = "winner")
	private long winner;
	@Column(name = "champion")
	private Integer champion;
	@Column(name = "bombardier")
	private long bombardier;
	@Column(name = "prognosis_playoff")
	private Integer prognosisPlayoff;
	@Column(name = "score_playoff")
	private long scorePlayoff;
	@Column(name = "difference_playoff")
	private Integer differencePlayoff;
	@Column(name = "winner_playoff")
	private Integer winnerPlayoff;
	@Column(name = "team_placing")
	private Integer teamPlacingTeam;
	@Column(name = "prognosis_1_4")
	private Integer prognosis_1_4;
	@Column(name = "prognosis_1_2")
	private Integer prognosis_1_2;
	@Column(name = "points")
	private Integer points;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usr", referencedColumnName = "id")
	private Users usr;
}