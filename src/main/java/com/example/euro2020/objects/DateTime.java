package com.example.euro2020.objects;

import com.example.euro2020.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DateTime {

	private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss_dd.MM.yyyy");
	private ConfigService configService;
	private Timestamp timestamp;
	private String date;

	public DateTime () {
		Date now = new Date();
		timestamp = new Timestamp(now.getTime());
	}

	public DateTime (String dateString) {
		Date date;
		try {
			date = formatter.parse(dateString);
			Timestamp timestamp = new Timestamp(date.getTime());
			DateTime d = new DateTime(timestamp.getTime());
			this.timestamp = d.getTimestamp();
			this.date = d.getDate();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public DateTime (long ts) {
		Date now = new Date();
		now.setTime(ts);
		long time = now.getTime();
		timestamp = new Timestamp(time);
		date = formatter.format(time);
	}

	@Autowired
	public DateTime (ConfigService configService) {
		this.configService = configService;
	}

	public static String getDayName (Timestamp timestamp) {
		DateFormat formatter = new SimpleDateFormat("EEEE", Locale.getDefault());
		return formatter.format(timestamp);
	}

	public static Long getTime (Object time) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
			Date parsedDate = dateFormat.parse(String.valueOf(time));
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	public Timestamp getTimestamp () {
		return timestamp;
	}

	public String getDate () {
		return date;
	}

	public SimpleDateFormat getFormatter () {
		return formatter;
	}

	public Timestamp getTimestampMatch (String date, String time) {
		int month = 0;
		switch (MyMatcher.find(date, "(\\D*)").get(0)) {
			case "июня":
				month = 6;
				break;
			case "июля":
				month = 7;
				break;
		}
		int day = Integer.parseInt(MyMatcher.find(date, "(\\d*)").get(0));
		Integer hour = Integer.valueOf(MyMatcher.find(time, "(\\d*)").get(0));

		return new DateTime(hour + ":00:00_" + day + ".0" + month + "." + configService.getYearMatches()).getTimestamp();
	}

	public TimeStartFinish getTimeStartFinish (Long now) {
		Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/Moscow"));
		calendar.setTime(new Date(now));
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.AM_PM, 0);
		long today = calendar.getTimeInMillis();
		long yesterday = today - 24 * 3600 * 1000;
		long tomorrow = today + 24 * 3600 * 1000;
		TimeStartFinish timeStartFinish = new TimeStartFinish();
		timeStartFinish.setStart(today);
		timeStartFinish.setFinish(tomorrow);
		if (now < today && now > yesterday) {
			timeStartFinish.setStart(yesterday);
			timeStartFinish.setFinish(today);
		}
		return timeStartFinish;
	}
}
