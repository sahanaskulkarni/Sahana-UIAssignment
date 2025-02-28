package com.example.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MPointsDTO {
	
	private int year;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int month;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String monthOfYear;
	
	private Long points;
	
	public MPointsDTO() {
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public MPointsDTO(int year,int month, Long points) {
		this.year = year;
		this.month = month;
		this.points = points;
	
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public Long getPoints() {
		return points;
	}
	public void setPoints(Long points) {
		this.points = points;
	}
	public String getMonthOfYear() {
		return monthOfYear;
	}
	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear;
	}
}




