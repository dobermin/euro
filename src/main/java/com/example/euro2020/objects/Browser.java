package com.example.euro2020.objects;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class Browser {

	private String browser = "Неизвестный";
	private String os = "Неизвестная";

	public Browser (HttpServletRequest request) {
		String browser = request.getHeader("user-agent");
		List<String> browsersList = new ArrayList<>() {
			{
				add("Firefox");
				add("Opera");
				add("OPR");
				add("Chrome");
				add("MSIE");
				add("Safari");
			}
		};
		for (String b : browsersList) {
			if (browser.contains(b)) {
				switch (b) {
					case "OPR":
						this.browser = "Opera";
						break;
					case "MSIE":
						this.browser = "Internet Explorer";
						break;
					default:
						this.browser = b;
						break;
				}
				break;
			}
		}
		List<String> osList = new ArrayList<>() {
			{
				add("Windows");
				add("iPhone");
				add("Macintosh");
				add("iPad");
				add("Android");
				add("Linux");
			}
		};
		for (String b : osList) {
			if (browser.contains(b)) {
				switch (b) {
					case "iPhone":
					case "iPad":
						this.os = "iOS";
						break;
					case "Macintosh":
						this.os = "macOS";
						break;
					default:
						this.os = b;
						break;
				}
				break;
			}
		}
	}

	public String getBrowser () {
		return browser;
	}

	public String getOs () {
		return os;
	}
}
