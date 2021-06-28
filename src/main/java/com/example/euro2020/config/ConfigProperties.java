package com.example.euro2020.config;

import com.example.euro2020.entity.Navigation;
import com.example.euro2020.entity.Party;
import com.example.euro2020.entity.Prognosis;
import com.example.euro2020.objects.Jsoup;
import com.example.euro2020.objects.MyMatcher;
import com.example.euro2020.service.ConfigService;
import lombok.Data;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Configuration
@Data
public class ConfigProperties {

	public final ConfigService configService;
	private final String OVERTIME = "овертайм";
	private final String PENALTY = "пенальти";
	private HashMap<String, List<Navigation>> navigation = new HashMap<>() {
		{
			put("Группы", new ArrayList<>() {
				{
					add(new Navigation("", "groups"));
				}
			});
			put("Авторизация", new ArrayList<>() {
				{
					add(new Navigation("", "authorization"));
				}
			});
			put("Регистрация", new ArrayList<>() {
				{
					add(new Navigation("", "registration"));
				}
			});
		}
	};

	public ConfigProperties (ConfigService configService) {
		this.configService = configService;
	}

	public HashMap<String, List<Navigation>> getNavigation () {
		return (HashMap<String, List<Navigation>>) navigation.clone();
	}

	public Document getDocFromCalendar () {
		return new Jsoup(configService.getCalendar()).getDocument();
	}

	public Document getDocFromBombardier () {
		return new Jsoup(configService.getBombardier()).getDocument();
	}

	public Document getDocFromTeams (String team) {
		return new Jsoup(configService.getTeams() + team + ".htm").getDocument();
	}

	public Document getDocByGroup (Party party) {
		String group = party.getParty().toLowerCase();
		group = group.split("\\s")[1];
		switch (group) {
			case "а":
				group = "a";
				break;
			case "в":
				group = "b";
				break;
			case "с":
				group = "c";
				break;
			case "е":
				group = "e";
				break;
		}
		String uri = configService.getUrl() + group + ".htm";
		return new Jsoup(uri).getDocument();
	}

	public List<String> getListFromElements (Elements elements, String attr) {
		List<String> list = new ArrayList<>();
		for (Element element : elements) {
			if ("src".equals(attr)) {
				list.add(MyMatcher.find(element.attr(attr), "/[a-z]*").get(2).substring(1));
			} else {
				try {
					list.add(element.parent().text());
				} catch (Exception e) {
					list.add(element.text());
				}
			}
		}
		return list;
	}

	public List<String> getListFromElements (Elements elements) {
		return getListFromElements(elements, "");
	}

	public String getCountry (String country) {
		country = country.replaceAll("\\.", ". ");
		country = country.replaceAll("\\s{2}", " ");
		switch (country) {
			case "Северная Македония":
				return "С. Македония".toUpperCase();
			case "Северная Ирландия":
				return "С. Ирландия".toUpperCase();
		}
		return country.toUpperCase();
	}

	public Integer generateNumber (int number) {
		int n = (int) Math.pow(2, number);
		int sum = n * 2 - 1;
		List<Double> list = new ArrayList<>();
		list.add(100.0 * n / sum);
		for (int i = number - 1; i >= 0; i--) {
			list.add(list.get(list.size() - 1) + Math.pow(2, i) * 100 / sum);
		}
		int random = new Random().nextInt(101);
		for (int i = 0; i < list.size(); i++) {
			if (random < list.get(i)) return i;
		}
		return number;
	}

	public Integer generateNumber () {
		return generateNumber(5);
	}

	public List<Integer> randomPlaces () {
		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		Collections.shuffle(list);
		return list;
	}

	public String getPointsClass (int points, long time) {
		if (time >= configService.getCupEightStart()) {
			if (
				configService.getScorePO() + configService.getNextRoundPO() == points
			) return "lime";
			if (
				configService.getScorePO() == points
			) return "lightgreen";
			if (
				configService.getDifferencePO() + configService.getNextRoundPO() == points
			) return "yellow";
			if (
				configService.getDifferencePO() == points
			) return "lightblue";
			if (
				configService.getWinnerPO() + configService.getNextRoundPO() == points
			) return "darkorange";
			if (
				configService.getWinnerPO() == points
			) return "lightcoral";
		} else {
			if (
				configService.getScore() == points
			) return "lime";
			if (
				configService.getDifference() == points
			) return "yellow";
			if (
				configService.getWinner() == points
			) return "lightcoral";
		}

		return "";
	}

	public List<String> getColorClass (List<Prognosis> prognoses) {
		List<String> color = new ArrayList<>();
		for (Prognosis p : prognoses)
			try {
				color.add(getPointsClass(p.getPoints(), p.getMatch().getTimestamp().getTime()));
			} catch (Exception e) {
				color.add("");
			}
		return color;
	}

	public List<List<String>> getColorBeforeClass (List<List<Prognosis>> prognoses) {
		try {
			List<List<String>> list = new ArrayList<>();
			prognoses.forEach(
				s -> {
					list.add(getColorClass(s));
				}
			);
			return list;
		} catch (Exception e) {
			return null;
		}
	}
}
