package com.azf.dev.model.dto.request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

public class CompanyRequest {

	private Long id;

	@NotEmpty(message = "label is mandatory, it cannot be empty")
	private String label;

	@NotEmpty(message = " date is mandatory, it cannot be empty")
	private Date date;

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

}
