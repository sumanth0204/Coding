package com.inventory.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.inventory.entities.Item;

/**
 * Inventory Store is the datasource for storing the inventory information
 * Since DAO is singleton we will have one store at max
 *  */
public class InventoryStore {

	private ConcurrentMap<String,Item> store=null;

	private Map<String,Item> deletedItems=null;
	
	public Map<String, Item> getDeletedItems() {
		return deletedItems;
	}

	public Map<String, Item> getStore() {
		return store;
	}
	
	InventoryStore(){
		store=new ConcurrentHashMap<String,Item>();
		deletedItems=new HashMap<String,Item>();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * returns the deep copy of the store for report of a Inventory
	 * to prevent the state modifications of the store.
	 * @return
	 */

	public Map<String,Item> getStoreCopy(){
		Map<String,Item> copy=new HashMap<String,Item>();
		if(store.size()>1) {
			store.forEach((key,val)->{
				copy.put(key, new Item(val.getItemName(),val.getCostPrice(),val.getSellingPrice(),val.getQuantity(),val.getNoOfItemsSold()));
				val.setNoOfItemsSold(0);
			});
			
			return copy;	
		}else {
			return null;
		}
	}
}