package com.inventory.entities;


public class Item {
	private String itemName;

	private Double costPrice; //Bought At

	private Double  sellingPrice; //Sold At

	private Integer quantity;

	private Integer noOfItemsSold;
	
	private Integer totalNoOfItemsSold;

	public Item(String itemName,Double costPrice,Double sellingPrice,Integer quantity){
		this.itemName=itemName;
		this.costPrice=costPrice;
		this.sellingPrice=sellingPrice;
		this.quantity=quantity;
		this.noOfItemsSold=0;
		this.totalNoOfItemsSold=0;
	}
	
	public Item(String itemName,Double costPrice,Double sellingPrice,Integer quantity,Integer noOfItemsSold){
		this.itemName=itemName;
		this.costPrice=costPrice;
		this.sellingPrice=sellingPrice;
		this.quantity=quantity;
		this.noOfItemsSold=noOfItemsSold;
		this.totalNoOfItemsSold=0;
	}

	public Item(String itemName,Double costPrice,Double sellingPrice,Integer quantity,Integer noOfItemsSold, Integer totalNoOfItemsSold){
		this.itemName=itemName;
		this.costPrice=costPrice;
		this.sellingPrice=sellingPrice;
		this.quantity=quantity;
		this.noOfItemsSold=noOfItemsSold;
		this.totalNoOfItemsSold=totalNoOfItemsSold;
	}

	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getNoOfItemsSold() {
		return noOfItemsSold;
	}

	public void setNoOfItemsSold(Integer noOfItemsSold) {
		this.noOfItemsSold = noOfItemsSold;
	}
	
	public Integer getTotalNoOfItemsSold() {
		return totalNoOfItemsSold;
	}

	public void setTotalNoOfItemsSold(Integer totalNoOfItemsSold) {
		this.totalNoOfItemsSold = totalNoOfItemsSold;
	}
	
/*	@Override
	public String toString() {
		return "{ \"itemName \" : \""+this.itemName+"\","
				+"\"costPrice \" : \""+this.costPrice+"\","
				+"\"sellingPrice \" : \""+this.sellingPrice+"\","
				+"\"quantiry \" : \""+this.quantity+"\"}";
	}*/
	
	@Override
	public String toString() {
		return this.itemName+"        "+this.costPrice+"        "+this.sellingPrice+"        "+this.quantity;
	}
}