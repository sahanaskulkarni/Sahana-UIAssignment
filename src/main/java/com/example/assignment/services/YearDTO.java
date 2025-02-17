package com.example.assignment.services;

import java.util.List;


public class YearDTO {
	private int year;
	private List<MonthDTO> data;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<MonthDTO> getData() {
		return data;
	}
	public void setData(List<MonthDTO> data) {
		this.data = data;
	}
}
