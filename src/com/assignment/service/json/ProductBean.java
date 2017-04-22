/**
 * 
 */
package com.assignment.service.json;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * @author Deepa Joshi
 * @version 1.0
 * @since 22-04-2017
 */
@ManagedBean
@SessionScoped
public class ProductBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static List<Product> products;
	private List<Product> selectedProducts = new ArrayList<Product>();
	private Product product;
	public String selectedProductName;
	public int selectedProductQuantity;
	public double orderProductPrice;
	public double totalAmountPayable;

	static {
		//We want to do this only once, hence static block.
		try {

			ClientConfig clientConfig = new DefaultClientConfig();

			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

			Client client = Client.create(clientConfig);

			WebResource webResource = client.resource("https://api.myjson.com/bins/gx6vz");

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String jsonInString = response.getEntity(String.class);
			Type listType = new TypeToken<Map<String, ArrayList<Product>>>() {
			}.getType();
			Gson gson = new GsonBuilder().create();
			Map<String, ArrayList<Product>> treeMap = gson.fromJson(jsonInString, listType);
			ArrayList<Product> products = treeMap.get("prices");
			setProducts(products);
			// System.out.println("Server response .... \n");
			//System.out.println(products);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Adds the user selected product to ordered product list
	 * 
	 * @param selectedProductName
	 * 
	 * @param selectedProductQuantity
	 * 
	 */
	public void addProduct(String selectedProductName, int selectedProductQuantity) {
		Product addProduct = null;

		this.selectedProductName = selectedProductName;
		for (Product p : getProducts()) {
			if (p.getName().equals(selectedProductName)) {
				Product productFromCartWithSelectedNm = getProductOrdered(selectedProductName);
				if (productFromCartWithSelectedNm == null) {
					addProduct = new Product(selectedProductName, selectedProductQuantity);
					calculateProductPrice(addProduct);
				} else {
					productFromCartWithSelectedNm.setOrderQuantity(
							productFromCartWithSelectedNm.getOrderQuantity() + selectedProductQuantity);
					calculateProductPrice(productFromCartWithSelectedNm);
				}
				break;
			}
		}
		selectedProducts.add(addProduct);
		setSelectedProductName(getProducts().get(0).getName());
		setSelectedProductQuantity(0);
	}

	// value change listener for list item selection
	public void valueChanged(ValueChangeEvent e) {
		selectedProductName = (String) e.getNewValue();
	}

	/*
	 * Caclulate the total order price on check out.
	 */
	public void checkOut() {
		for (Product coProduct : selectedProducts) {
			totalAmountPayable = totalAmountPayable + coProduct.getOrderProductPrice();
		}
	}

	/*
	 * @param coProduct Product to be checked out
	 */
	private void calculateProductPrice(Product coProduct) {
		double pSpecialPrice = 0.0f;
		//Check the price applicable by calling getProducts method which has cached the product information 
		for (Product p : getProducts()) {
			//Check which product user has selected from list of prodcuts by matching their name.
			if (p.getName().equals(coProduct.getName())) {
				if (p.getSpecial_qty() != 0) {
					
					if (coProduct.getOrderQuantity() < p.getSpecial_qty()) {
						coProduct.setOrderProductPrice(coProduct.getOrderQuantity() * p.unit_price);
					} else if (coProduct.getOrderQuantity() == p.getSpecial_qty()) {
						coProduct.setOrderProductPrice(p.getSpecial_price());
					} else {
						//First we find how many items will not get special quantity price 
						int remIndividualItems = coProduct.getOrderQuantity() % p.getSpecial_qty();
						
						//Number of items which will get discounted price.
						int numberOfDiscountedGrp = (coProduct.getOrderQuantity()
								- (coProduct.getOrderQuantity() % p.getSpecial_qty())) / (p.getSpecial_qty());
						
						pSpecialPrice = (numberOfDiscountedGrp * p.getSpecial_price())
								+ (remIndividualItems * p.unit_price);
						
						//Set the price on checkout product for UI to display.
						coProduct.setOrderProductPrice(pSpecialPrice);
					}
				} else {
					coProduct.setOrderProductPrice(coProduct.getOrderQuantity() * p.unit_price);
				}
				break;
			}
		}

	}

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public static void setProducts(List<Product> products) {
		ProductBean.products = products;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the selectedProductName
	 */
	public String getSelectedProductName() {
		return selectedProductName;
	}

	/**
	 * @param selectedProductName
	 *            the selectedProductName to set
	 */
	public void setSelectedProductName(String selectedProductName) {
		this.selectedProductName = selectedProductName;
	}

	/**
	 * @return the selectedProductQuantity
	 */
	public int getSelectedProductQuantity() {
		return selectedProductQuantity;
	}

	/**
	 * @param selectedProductQuantity
	 *            the selectedProductQuantity to set
	 */
	public void setSelectedProductQuantity(int selectedProductQuantity) {
		this.selectedProductQuantity = selectedProductQuantity;
	}

	/**
	 * @return the selectedProducts
	 */
	public List<Product> getSelectedProducts() {
		return selectedProducts;
	}

	/**
	 * @return the orderProductPrice
	 */
	public double getOrderProductPrice() {
		return orderProductPrice;
	}

	/**
	 * @param orderProductPrice
	 *            the orderProductPrice to set
	 */
	public void setOrderProductPrice(double orderProductPrice) {
		this.orderProductPrice = orderProductPrice;
	}

	/**
	 * @param selectedProducts
	 *            the selectedProducts to set
	 */
	public void setSelectedProducts(List<Product> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	/**
	 * @return the totalAmountPayable
	 */
	public double getTotalAmountPayable() {
		return totalAmountPayable;
	}

	/**
	 * @param totalAmountPayable
	 *            the totalAmountPayable to set
	 */
	public void setTotalAmountPayable(double totalAmountPayable) {
		this.totalAmountPayable = totalAmountPayable;
	}

	/*
	 * Retrieves the product from list of available products
	 * 
	 * @param productName
	 * 
	 * @return Product
	 */
	private Product getProductOrdered(String productName) {
		Product productFromCart = null;
		for (Product p : selectedProducts) {
			if (p.getName().equals(productName)) {
				productFromCart = p;
				break;
			}
		}
		return productFromCart;
	}
}
