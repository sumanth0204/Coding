package com.inventory.datasource;

import java.util.Map;

import com.inventory.entities.Item;

/**
 * DBAccess used for performing the CRUD operations on the InventoryStore
 * If we are using the DB for InventoryStore always better to take the advantage of DB transaction' s feature
 */
public class DBAccess implements DAOService{

	private static DBAccess dbAccess=null;
	
	private static Map<String,Item> store=null;
	
	private static Map<String,Item> deleted=null;
	
	private static InventoryStore inventoryStore=null;
	
	private DBAccess(){
		
	}
	
	public static synchronized DBAccess getInstance() {
		 if(dbAccess==null) {
			inventoryStore=new InventoryStore();
			store=inventoryStore.getStore();
			deleted=inventoryStore.getDeletedItems();
			dbAccess=new DBAccess();
		 }
		return dbAccess;
	}

	@Override
	public boolean createItem(String itemName, Double costPrice, Double sellingPrice) {
		if(store.containsKey(itemName)) {
			Item item=store.get(itemName);
			item.setQuantity(item.getQuantity()+1);
			store.put(itemName,item);
		}else {
			store.put(itemName, new Item(itemName,costPrice,sellingPrice,0));
		}
		return true;
	}

	@Override
	public boolean deleteItem(String itemName) {
		deleted.put(itemName, store.get(itemName));	
		store.remove(itemName);
		return true;
	}

	@Override
	public boolean updateBuy(String itemName, Integer quantity) {
		Item item=store.get(itemName);
		item.setQuantity(item.getQuantity()+quantity);
		store.put(itemName, item);
		return true;
	}

	@Override
	public boolean updateSell(String itemName, Integer quantity) {
		Item item=store.get(itemName);
		if((item.getQuantity()-quantity)<=0) {
			item.setQuantity(0);
		}else {
			item.setQuantity(item.getQuantity()-quantity);
		}
		item.setNoOfItemsSold(item.getNoOfItemsSold()+quantity);
		item.setTotalNoOfItemsSold(item.getTotalNoOfItemsSold()+quantity);
		store.put(itemName, item);
		return true;
	}
	
	@Override
	public boolean updateSellPrice(String itemName, Double newSellingPrice) {
		Item item=null;
		item=store.get(itemName);
		if(item!=null) {
			item.setSellingPrice(newSellingPrice);
			store.put(itemName, item);
			return true;
		}
		return false;
	}
	
	@Override
	public Map<String, Item> getData() {
		return inventoryStore.getStoreCopy();
	}

	@Override
	public Map<String, Item> getDeletedItems() {
		return deleted;
	}	
}