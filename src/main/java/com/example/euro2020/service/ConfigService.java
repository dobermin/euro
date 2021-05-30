package com.example.euro2020.service;

import com.example.euro2020.entity.Config;
import com.example.euro2020.objects.DateTime;
import com.example.euro2020.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConfigService {

	@Autowired
	private ConfigRepository repository;

	public Config findAll () {
		return new ArrayList<>((List<Config>) repository.findAll()).get(0);
	}

	public void update (Config config) {
		repository.deleteAll();
		repository.save(config);
	}

	public Integer getYear () {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public String getTitle () {
		return findAll().getTitle();
	}

	public Integer getYearMatches () {
		return findAll().getYearMatches();
	}

	public String getHost () {
		return findAll().getHost() + "/";
	}

	public String getUrl () {
		return getHost() + findAll().getUrl() + "/";
	}

	public String getCalendar () {
		return getUrl() + findAll().getCalendar();
	}

	public String getBombardier () {
		return getUrl() + findAll().getBombardier();
	}

	public String getSrc () {
		return findAll().getSrc();
	}

	public String getTeams () {
		return getUrl() + findAll().getTeams() + "/";
	}

	public Integer getScore () {
		return findAll().getScore();
	}

	public Integer getScorePO () {
		return findAll().getScorePO();
	}

	public Integer getNextRoundPO () {
		return findAll().getNextRoundPO();
	}

	public Integer getDifference () {
		return findAll().getDifference();
	}

	public Integer getDifferencePO () {
		return findAll().getDifferencePO();
	}

	public Integer getWinner () {
		return findAll().getWinner();
	}

	public Integer getWinnerPO () {
		return findAll().getWinnerPO();
	}

	public Integer getPlace () {
		return findAll().getPlace();
	}

	public Integer getPrognosisQuarter () {
		return findAll().getPrognosisQuarter();
	}

	public Integer getPrognosisSemi () {
		return findAll().getPrognosisSemi();
	}

	public Integer getChampionPoints () {
		return findAll().getChampionPoints();
	}

	public Integer getBombardierPoints () {
		return findAll().getBombardierPoints();
	}

	private Long getCupStart () {
		return DateTime.getTime(findAll().getCupStart() + "." + getYearMatches());
	}

	private Long getCupTimePreview () {
		return findAll().getCupTimePreview() * 3600L * 1000;
	}

	public Boolean getTesting () {
		return findAll().getTesting();
	}

	private Long getTimeTestNow () {
		return DateTime.getTime(findAll().getTimeTestNow());
	}

	private Long getTimeTestStart () {
		return DateTime.getTime(findAll().getTimeTestStart());
	}

	public Long getTimeNow () {
		long time = new Date().getTime();
		if (getTesting()) {
			time += getTimeTestNow() - getTimeTestStart();
		}
		return time;
	}

	public boolean isEnable () {
		if (isWeekend()) return true;

		Long timeNow = getTimeNow();
		Long timeOpen = getTimeOpen();
		Long timeClose = getTimeClose();
		return !(timeNow < timeOpen &&
			timeNow > timeClose);
	}

	public boolean canRegistration () {
		return getTimeNow() <= (getCupStart() - getCupTimePreview() - 3600L * 1000);
	}


	private String getDate () {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		return format.format(new Date().getTime());
	}

	private boolean isWeekend () {
		SimpleDateFormat format = new SimpleDateFormat("EEEE");
		String str = format.format(new Date().getTime());
		return str.equals("воскресенье") ||
			str.equals("суббота");
	}

	private Long getTimeClose () {
		return DateTime.getTime(findAll().getTimeEndSiteShow() + " " + getDate());
	}

	private Long getTimeOpen () {
		return DateTime.getTime(findAll().getTimeStartSiteShow() + " " + getDate());
	}

	public boolean timeOutStartCup () {
		return !(getTimeNow() <= getCupStart() - getCupTimePreview());
	}

	public boolean timeOutEndCup () {
		return !(getTimeNow() <= getCupEnd() - getCupTimePreview());
	}

	public boolean timeAfterFirstMatch () {
		return (getTimeNow() > getCupStart() + 3 * getCupTimePreview());
	}

	public Long timeOutStartMatch () {
		return getTimeNow() + getCupTimePreview();
	}

	public boolean cupGroupsEnd () {
		return getTimeNow() > getCupGroupsEnd();
	}

	public Long getCupGroupsEnd () {
		return DateTime.getTime(findAll().getCupGroupsEnd() + "." + getYearMatches());
	}

	public Long getCupEightStart () {
		return DateTime.getTime(findAll().getCupEightStart() + "." + getYearMatches());
	}

	public Long getCupEightEnd () {
		return DateTime.getTime(findAll().getCupEightEnd() + "." + getYearMatches());
	}

	public Long getCupFourStart () {
		return DateTime.getTime(findAll().getCupFourStart() + "." + getYearMatches());
	}

	public Long getCupFourEnd () {
		return DateTime.getTime(findAll().getCupFourEnd() + "." + getYearMatches());
	}

	public Long getCupEnd () {
		return DateTime.getTime(findAll().getCupEnd() + "." + getYearMatches());
	}
}
