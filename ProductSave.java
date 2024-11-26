package com.mydata.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;


public class ProductSave {
	
	@NotEmpty(message="Name Is Required")
	private String name;
	
	@NotEmpty(message="Brand Is Required")
	private String brand;
	
	@NotEmpty(message="Category Is Required")
	private String category;
	
	@Min(0)
	private double price;
	
	@Size(min=10, message="The Desacription should be at least 10 cahracters")
	@Size(max=2000, message="The Desacription cannot exceed 2000 cahracters")
	private String description;
	
	private MultipartFile imageFile;

	// setter getter method
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}


	// constructors
	public ProductSave(@NotEmpty(message = "Name Is Required") String name,
			@NotEmpty(message = "Brand Is Required") String brand,
			@NotEmpty(message = "Category Is Required") String category, @Min(0) double price,
			@Size(min = 10, message = "The Desacription should be at least 10 cahracters") @Size(max = 2000, message = "The Desacription cannot exceed 2000 cahracters") String description,
			MultipartFile imageFile) {
		super();
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.price = price;
		this.description = description;
		this.imageFile = imageFile;
	}
	
	public ProductSave() {}

	//toString 
	@Override
	public String toString() {
		return "ProductSave [name=" + name + ", brand=" + brand + ", category=" + category + ", price=" + price
				+ ", description=" + description + ", imageFile=" + imageFile + "]";
	}
	
	
}
