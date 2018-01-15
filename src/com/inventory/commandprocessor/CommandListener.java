package com.inventory.commandprocessor;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class CommandListener {

	private static final boolean verbose=false;

	private static final boolean debug=false;

	/**
	 * infinite loop that creates a listener 
	 * listens to the command and parse the command with parser
	 * listen can be an rest end point to receive the message (with out while loop)
	 */
	public void listen() {
		Scanner sc=new Scanner(System.in);

		System.out.println("======================================="
				+ "Mr.X Inventory System"
				+ "CREATE - create itenName costPrice sellingPrice \n"
				+ "DELETE - delete itemName \n"
				+ "UPDATE BUY - updateBuy itemName quantity"
				+ "UPDATE SELL - updateSell itemName quantity\n"
				+ "=======================================\n");
		System.out.println("Waiting for input command...");
		while(true) {
			String command=sc.next();
			CompletableFuture<Void> future
			= CompletableFuture.runAsync(new Runnable() {
				@Override
				public void run() {
					if(verbose) System.out.println(command);
					new CommandParser().handleRequest(command);
				}				  
			});
			//future.get(); to make it synchronous
		}	
	}
}