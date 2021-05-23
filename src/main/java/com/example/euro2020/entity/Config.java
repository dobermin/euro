package com.example.euro2020.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Config {

	@Id
	private Long id = 1L;

	@Column(name = "year_matches")
	private Integer yearMatches;

	@Column
	private String title;

	@Column
	private String host;

	@Column
	private String url;

	@Column
	private String calendar;

	@Column
	private String bombardier;

	@Column
	private String src;

	@Column
	private String teams;

	@Column
	private Integer score;

	@Column(name = "score_play_off")
	private Integer scorePO;

	@Column
	private Integer difference;

	@Column(name = "difference_play_off")
	private Integer differencePO;

	@Column
	private Integer winner;

	@Column(name = "winner_play_off")
	private Integer winnerPO;

	@Column(name = "next_round_play_off")
	private Integer nextRoundPO;

	@Column
	private Integer place;

	@Column(name = "prognosis_quarter")
	private Integer prognosisQuarter;

	@Column(name = "prognosis_semi")
	private Integer prognosisSemi;

	@Column(name = "champion_points")
	private Integer championPoints;

	@Column(name = "bombardier_points")
	private Integer bombardierPoints;

	@Column(name = "cup_start")
	private String cupStart;

	@Column(name = "time_test_start")
	private String timeTestStart;

	@Column(name = "time_test_now")
	private String timeTestNow;

	@Column(name = "cup_groups_end")
	private String cupGroupsEnd;

	@Column(name = "cup_eight_start")
	private String cupEightStart;

	@Column(name = "cup_eight_end")
	private String cupEightEnd;

	@Column(name = "cup_four_start")
	private String cupFourStart;

	@Column(name = "cup_four_end")
	private String cupFourEnd;

	@Column(name = "cup_end")
	private String cupEnd;

	@Column(name = "cup_time_preview")
	private Integer cupTimePreview;

	@Column(name = "time_start_site_show")
	private String timeStartSiteShow;

	@Column(name = "time_end_site_show")
	private String timeEndSiteShow;

	@Column(name = "testing")
	private Boolean testing;
}
