package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Table(
	name = "matches",
	schema = "public",
	uniqueConstraints=
	@UniqueConstraint(columnNames={"timestamp", "team_home"})
)
@Entity
@Data
public class Matches extends MyEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@JoinColumn(name = "timestamp")
	private Timestamp timestamp;
	@Column(name = "score_home")
	//счет матча
	private String scoreHome;
	@Column(name = "score_away")
	private String scoreAway;
	@Column(name = "main_home")
	//в основное время
	private String mainHome;
	@Column(name = "main_away")
	private String mainAway;
	@Column(name = "overtime")
	private String overtime;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "team_home", referencedColumnName = "id")
	private Teams teamHome;
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "team_away", referencedColumnName = "id")
	private Teams teamAway;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "tour")
	private Tour tour;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "next", referencedColumnName = "id")
	private Teams next;

	@Override
	public String toString () {
		return "Matches{}";
	}
}