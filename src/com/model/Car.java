package com.model;

public class Car {

	private int id;
	private String brand,model,registrationNo;
	private boolean available;
	private double pricePerDay;
	
	public Car() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Car(int id, String brand, String model, String registrationNo, boolean available, double pricePerDay) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.registrationNo = registrationNo;
		this.available = available;
		this.pricePerDay = pricePerDay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	
	
}
