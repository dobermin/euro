package com.example.euro2020.security.model.enums;

public enum Status {
	ACTIVE, BANNED;

	private String status;

	Status (String status) {
		this.status = status;
	}

	Status () {

	}

	public String getStatus () {
		return status;
	}

	@Override
	public String toString () {
		return status;
	}
}
