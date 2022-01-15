package com.azf.dev.model.dto;

import java.util.Date;

public class CompanyDto {

	private Long id;

	private String label;

	private Date date;

	public CompanyDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyDto(Long id, String label, Date date) {
		super();
		this.id = id;
		this.label = label;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", label=" + label + ", date=" + date + "]";
	}

}
