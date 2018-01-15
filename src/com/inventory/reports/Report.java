package com.inventory.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inventory.datasource.DBAccess;
import com.inventory.entities.Item;

public class Report {

	DBAccess dbAccess=DBAccess.getInstance();

	private static Report report=null;

	private static final boolean debug=true;
	
	private Report() {

	}

	public static synchronized Report getInstance() {
		if(report==null) {
			report=new Report();
		}
		return report;
	} 

	public void printReport() {
		Map<String,Item> data=dbAccess.getData();
		Map<String,Item> deletedItems=dbAccess.getDeletedItems();

		double total=0,profit=0;
		System.out.println("\n\n\n");
		System.out.println("INVENTORY REPORT");
		System.out.println("ItemName      BoughtAt      SoltAt      Availability     Value");
		System.out.println("--------      --------      ------      ------------     -----");

		List<String> list1=new ArrayList<String>(data.keySet());
		List<String> list2=new ArrayList<String>(deletedItems.keySet());
		Collections.sort(list1);
		Collections.sort(list2);
		
		
		if(list1.size()>=1) {
			for(String key:list1) {
				Item item=data.get(key);
				System.out.println(item.toString()+"        "+item.getQuantity()*item.getCostPrice());
				total=total+(item.getQuantity()*item.getCostPrice());
				
				if(deletedItems.containsKey(key)) {
					//if(debug)System.out.println("item.getSellingPrice()-item.getCostPrice(): "+(item.getSellingPrice()-item.getCostPrice())+""+(item.getNoOfItemsSold()-deletedItems.get(key).getQuantity()));
					profit=profit+(((item.getSellingPrice()-item.getCostPrice())*item.getNoOfItemsSold())-(deletedItems.get(key).getQuantity()*deletedItems.get(key).getCostPrice()));
				}else{
					profit=profit+((item.getSellingPrice()-item.getCostPrice())*(item.getNoOfItemsSold()-0));
				}
			}
			if(debug)System.out.println("profit: "+profit);
			if(list2.size()>=1) {
				
				for(String key:list2) {
					Item item=deletedItems.get(key);
					profit=profit+((item.getSellingPrice()-item.getCostPrice())*item.getNoOfItemsSold())-(item.getQuantity() * item.getCostPrice());
				}	
			}			
		}		
		System.out.println("--------------------------------------------------------------");
		System.out.println("Total Value:                                             "+total);
		System.out.println("Profit Since previous report:                            "+profit);
	}

	public void displayItems() {
		Map<String,Item> data=dbAccess.getData();
		data.forEach((key,value)->{
			System.out.println(value.toString());
		});
	}

	public void displayDeletedItems() {
		Map<String,Item> deletedItems=dbAccess.getDeletedItems();
		deletedItems.forEach((key,value)->{
			System.out.println(value.toString());
		});
	}
}