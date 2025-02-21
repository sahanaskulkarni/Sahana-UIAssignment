package com.example.assignment.model;

public class MPointsDTO {
	private int month;
	private Long points;
	
	public MPointsDTO(int month, Long points) {
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
