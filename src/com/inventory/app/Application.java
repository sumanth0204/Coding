package com.inventory.app;

import java.util.concurrent.CompletableFuture;

import com.inventory.commandprocessor.CommandListener;
import com.inventory.service.InventoryService;

public class Application {
	
	private static final boolean verbose=true; 
	
	public static void main(String args[]) {
		
		CompletableFuture<Void> future
		= CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				if(verbose) System.out.println("Initiating the message Handler");
				InventoryService.getInstance().messageHandler();
			}				  
		});
		
		if(verbose) System.out.println("Initiating the Listener");
		new CommandListener().listen();
	}
}
