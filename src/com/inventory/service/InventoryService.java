package com.inventory.service;

import com.inventory.constants.InventoryConstants;
import com.inventory.datasource.DBAccess;
import com.inventory.entities.MessageQueue;
import com.inventory.reports.Report;

/**
 * Ref: http://tutorials.jenkov.com/java-util-concurrent/blockingqueue.html
 * 
 * to ensure the state of the ItemStore consistent in concurrency or multiple commands at a time
 * CommandParser Multiple commands pushes to a BlockingQueue & Single DAO thread reads the commands in a serial passion and executes the commands
 */
public class InventoryService {

	private static final boolean verbose=false;

	private static final boolean debug=false;

	private static DBAccess dbAccess=DBAccess.getInstance();

	private static boolean handleCommands=false;

	MessageQueue queue=MessageQueue.getInstance();

	private static InventoryService service=null;

	Report reportGen=Report.getInstance();

	private InventoryService() {

	}

	public static synchronized InventoryService getInstance() {
		if(service==null)
			service=new InventoryService();
		return service;
	}

	public void pushMessage(String msg) throws InterruptedException{
		queue.addMessage(msg);
	}

	public String popMessage() throws InterruptedException{
		return queue.getNextMessage();
	}


	public void messageHandler() {

		synchronized(this) { //make sure at max one while loop has to run

			if(!handleCommands) {
				handleCommands=true;
				System.out.println("procssing the commands from channelBus");
				while(true) {
					try {
						if(debug) System.out.println("wiating for input..");
						String str=this.popMessage();
						if(debug) System.out.println("iteration: "+str);
						String[] filter=str.split(" ");

						if(InventoryConstants.commands[0].equalsIgnoreCase(filter[0]) ) {
							this.createItem(filter);
							if(verbose) System.out.println("Processing Command CREATE");

						}else if(InventoryConstants.commands[1].equalsIgnoreCase(filter[0])) {
							this.delete(filter);
							if(verbose) System.out.println("Processing Command DELETE");
						}else if(InventoryConstants.commands[2].equalsIgnoreCase(filter[0])) {

							this.updateBuy(filter);
							if(verbose) System.out.println("Processing Command UPDATEBUY");
						}else if(InventoryConstants.commands[3].equalsIgnoreCase(filter[0])) {

							this.updateSell(filter);
							if(verbose) System.out.println("Processing Command UPDATESELL");

						}else if(InventoryConstants.commands[4].equalsIgnoreCase(filter[0])) {
							this.generateReport();
							if(verbose) System.out.println("Processing Command REPORT");
						}else if(InventoryConstants.commands[5].equalsIgnoreCase(filter[0])) {
							this.updateSellPrice(filter);
							if(verbose) System.out.println("Processing Command UPDATESELLPRICE");
						}else {
							System.out.println("Wrong Command");
						}

					} catch(InterruptedException e1) {
						System.out.println("Interrupted Exception while reading the message channel queue: "+e1.getMessage());
					}catch (Exception e2) {
						System.out.println("Unknown Exception while reading the message channel queue: "+e2.getMessage());
					}
				}
			}
		}
	} 


	public void createItem(String str[]) {
		if(verbose) { System.out.println(str[0]+", "+str[1]+", "+str[2]+", "+str[3]); }
		dbAccess.createItem(str[1], Double.valueOf(str[2]),Double.valueOf(str[3]));
	}

	public void delete(String str[]) {
		if(verbose) { System.out.println(str[0]+", "+str[1]); }
		dbAccess.deleteItem(str[1]);
	}

	public void updateBuy(String str[]) {
		if(verbose) { System.out.println(str[0]+", "+str[1]+", "+str[2]); }
		dbAccess.updateBuy(str[1], Integer.valueOf(str[2]));
	}

	public void updateSell(String str[]) {
		if(verbose) { System.out.println(str[0]+", "+str[1]+", "+str[2]); }
		dbAccess.updateSell(str[1], Integer.valueOf(str[2]));
	}
	
	public void updateSellPrice(String str[]) {
		if(verbose) { System.out.println(str[0]+", "+str[1]+", "+str[2]); }
		dbAccess.updateSellPrice(str[1], Double.valueOf(str[2]));
	}
	
	public void generateReport() {
		reportGen.printReport();
	}
}