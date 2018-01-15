package com.inventory.datasource;

import java.util.Map;

import com.inventory.entities.Item;

public interface DAOService {
	
	public boolean createItem(String itemName, Double costPrice,Double sellingPrice);
	
	public boolean deleteItem(String itemName);
	
	public boolean updateBuy(String itemName, Integer quantity);

	public boolean updateSell(String itemName, Integer quantity);
	
	public boolean updateSellPrice(String itemName, Double newSellingPrice);
	
	public Map<String,Item> getData();
	
	public Map<String,Item> getDeletedItems();
}
