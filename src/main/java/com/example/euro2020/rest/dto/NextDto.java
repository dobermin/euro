package com.example.euro2020.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class NextDto {

	private List<String> quarter;
	private List<String> semi;
	private List<String> placings;
	private boolean referer;
	private boolean blocked;
}
