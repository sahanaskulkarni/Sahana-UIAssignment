package com.example.assignment.model;

public class MPointsDTO {
	private int year;
	private int month;
	private Long points;
	
	
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
}
