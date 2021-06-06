package com.example.euro2020.rest.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Placing {

	private boolean blocked;
	private List<Map<String, List<Map<String, String>>>> placings;

}
