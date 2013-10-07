package org.inf325.ventarron.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "product")
public class Product implements Parcelable {
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

	public Product() {
	}
	
	public Product(Parcel in) {
		id = in.readInt();
		code = in.readString();
		name = in.readString();
		description = in.readString();
		quantity = in.readInt();
		price = in.readDouble();
	}
	
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(code);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeInt(quantity);
		dest.writeDouble(price);
	}
	
	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
		@Override
		public Product createFromParcel(Parcel source) {
			return new Product(source);
		}

		@Override
		public Product[] newArray(int size) {
			return new Product[size];
		}
	};
}
