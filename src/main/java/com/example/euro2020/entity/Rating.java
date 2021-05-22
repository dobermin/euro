package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "rating", uniqueConstraints = {@UniqueConstraint(columnNames = "usr")})
public class Rating extends MyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
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
	@NotNull
	private Users usr;

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public Integer getScore () {
		return score;
	}

	public void setScore (Integer score) {
		this.score = score;
	}

	public Integer getAllScore () {
		return allScore;
	}

	public void setAllScore (Integer allScore) {
		this.allScore = allScore;
	}

	public Integer getDifference () {
		return difference;
	}

	public void setDifference (Integer difference) {
		this.difference = difference;
	}

	public long getWinner () {
		return winner;
	}

	public void setWinner (long winner) {
		this.winner = winner;
	}

	public Integer getChampion () {
		return champion;
	}

	public void setChampion (Integer champion) {
		this.champion = champion;
	}

	public long getBombardier () {
		return bombardier;
	}

	public void setBombardier (long bombardier) {
		this.bombardier = bombardier;
	}

	public Integer getPrognosisPlayoff () {
		return prognosisPlayoff;
	}

	public void setPrognosisPlayoff (Integer prognosisPlayoff) {
		this.prognosisPlayoff = prognosisPlayoff;
	}

	public long getScorePlayoff () {
		return scorePlayoff;
	}

	public void setScorePlayoff (long scorePlayoff) {
		this.scorePlayoff = scorePlayoff;
	}

	public Integer getDifferencePlayoff () {
		return differencePlayoff;
	}

	public void setDifferencePlayoff (Integer differencePlayoff) {
		this.differencePlayoff = differencePlayoff;
	}

	public Integer getWinnerPlayoff () {
		return winnerPlayoff;
	}

	public void setWinnerPlayoff (Integer winnerPlayoff) {
		this.winnerPlayoff = winnerPlayoff;
	}

	public Integer getTeamPlacingTeam () {
		return teamPlacingTeam;
	}

	public void setTeamPlacingTeam (Integer teamPlacingTeam) {
		this.teamPlacingTeam = teamPlacingTeam;
	}

	public Integer getPrognosis_1_4 () {
		return prognosis_1_4;
	}

	public void setPrognosis_1_4 (Integer prognosis_1_4) {
		this.prognosis_1_4 = prognosis_1_4;
	}

	public Integer getPrognosis_1_2 () {
		return prognosis_1_2;
	}

	public void setPrognosis_1_2 (Integer prognosis_1_2) {
		this.prognosis_1_2 = prognosis_1_2;
	}

	public Integer getPoints () {
		return points;
	}

	public void setPoints (Integer points) {
		this.points = points;
	}
}