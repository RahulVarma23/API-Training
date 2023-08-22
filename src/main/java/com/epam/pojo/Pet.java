package com.epam.pojo;

public class Pet {
	
	int id;
	Category category;
	String name;
	String status;
	
	public Pet(int id, Category category, String name, String status) {
		super();
		this.id = id;
		this.category = category;
		this.name = name;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
