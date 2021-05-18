package com.example.euro2020.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyMatcher {

	public static List<String> find (String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		List<String> list = new ArrayList<>();
		while (matcher.find())
			if (!matcher.group().isEmpty())
				list.add(matcher.group().trim());

		return list;
	}
	public static List<Integer> find (String text, String regex, boolean integer) {
		List<String> list = find(text, regex);
		List<Integer> integerList = new ArrayList<>();
		if (integer) {
			for (String l : list) {
				integerList.add(Integer.valueOf(l.replaceAll(",\\s", "")));
			}
		}
		return integerList;
	}
	public static Integer Max (List<Integer> list) {
		Collections.sort(list);
		return list.get(list.size() - 1);
	}
}
