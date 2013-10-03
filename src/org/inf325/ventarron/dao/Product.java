package org.inf325.ventarron.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "product")
public class Product {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(unique = true, canBeNull = false)
	private String code;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField
	private String description;
	@DatabaseField(canBeNull = false)
	private int quantity;
	@DatabaseField(canBeNull = false)
	private double price;
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Depot depot;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}
	
	@Override
	public String toString() {
		return name + "  -  Bs. " + price;
	}
}
