package com.example.euro2020.objects;

import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class Jsoup {

	private String uri;
	private Document document;

	public Jsoup (String uri) {
		try {
			document = org.jsoup.Jsoup.connect(uri)
				.userAgent("Mozilla")
				.timeout(5000)
				.referrer("http://google.com")
				.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Jsoup () {
	}

	public Document getFromFile(File file) {
		try {
			return org.jsoup.Jsoup.parse(file, "UTF-8", "http://example.com/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Document getDocument () {
		return document;
	}
}
