/**
 * 
 */
package com.assignment.service.json;

/**
 * @author Deepa Joshi
 * @version 1.0
 * @since 22-04-2017
 *
 */
public class Product {
	String name;
	int unit_price;
	int special_qty;
	int special_price;
	
	int orderQuantity;
	double orderProductPrice;
	
	/**
	 * @param name
	 * @param unit_price
	 * @param special_qty
	 * @param special_price
	 */
	public Product(String name, int unit_price, int special_qty, int special_price) {
		super();
		this.name = name;
		this.unit_price = unit_price;
		this.special_qty = special_qty;
		this.special_price = special_price;
	}
	
	
	/**
	 * @param name
	 * @param orderQuantity
	 */
	public Product(String name, int orderQuantity) {
		super();
		this.name = name;
		this.orderQuantity = orderQuantity;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the unit_price
	 */
	public int getUnit_price() {
		return unit_price;
	}
	/**
	 * @param unit_price the unit_price to set
	 */
	public void setUnit_price(int unit_price) {
		this.unit_price = unit_price;
	}
	/**
	 * @return the special_qty
	 */
	public int getSpecial_qty() {
		return special_qty;
	}
	/**
	 * @param special_qty the special_qty to set
	 */
	public void setSpecial_qty(int special_qty) {
		this.special_qty = special_qty;
	}
	/**
	 * @return the special_price
	 */
	public int getSpecial_price() {
		return special_price;
	}
	/**
	 * @param special_price the special_price to set
	 */
	public void setSpecial_price(int special_price) {
		this.special_price = special_price;
	}
	
	
	/**
	 * @return the orderQuantity
	 */
	public int getOrderQuantity() {
		return orderQuantity;
	}


	/**
	 * @param orderQuantity the orderQuantity to set
	 */
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}


	/**
	 * @return the orderProductPrice
	 */
	public double getOrderProductPrice() {
		return orderProductPrice;
	}


	/**
	 * @param orderProductPrice the orderProductPrice to set
	 */
	public void setOrderProductPrice(double orderProductPrice) {
		this.orderProductPrice = orderProductPrice;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Prices [name=" + name + ", unit_price=" + unit_price + ", special_qty=" + special_qty
				+ ", special_price=" + special_price + "]";
	}
	
}
