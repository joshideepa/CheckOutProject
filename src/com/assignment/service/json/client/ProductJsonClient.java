/**
 * 
 */
package com.assignment.service.json.client;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.assignment.service.json.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.google.gson.reflect.TypeToken;

/**
 * @author Deepa Joshi
 * @version 1.0
 * @since 22-04-2017
 *
 */
public class ProductJsonClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			ClientConfig clientConfig = new DefaultClientConfig();

			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

			Client client = Client.create(clientConfig);

			WebResource webResource = client.resource("https://api.myjson.com/bins/gx6vz");

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			
			String jsonInString = response.getEntity(String.class);
			Type listType = new TypeToken<Map<String, ArrayList<Product>>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			Map<String, ArrayList<Product>> treeMap = gson.fromJson(jsonInString, listType);
			ArrayList<Product> objects = treeMap.get("prices");

			System.out.println("Server response .... \n");
			System.out.println(objects);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
