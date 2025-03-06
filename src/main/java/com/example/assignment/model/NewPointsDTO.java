package com.example.assignment.model;

public class NewPointsDTO {
	private int year;
	private int month;
	private int points;
	
	public NewPointsDTO(int year, int month, int points) {
		this.year = year;
		this.month = month;
		this.points = points;
	}
	
	public NewPointsDTO(NewPointsDTO dto) {
		this.year = dto.year;
		this.month = dto.month;
		this.points = dto.points;
	}

	public NewPointsDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
}
