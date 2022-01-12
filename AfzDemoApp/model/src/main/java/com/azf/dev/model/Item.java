package com.azf.dev.model;

public class Item {

	private Long id;

	private String label;

	private String date;

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(Long id, String label, String date) {
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", label=" + label + ", date=" + date + "]";
	}

}
