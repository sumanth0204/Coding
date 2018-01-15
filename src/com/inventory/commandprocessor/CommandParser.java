package com.inventory.commandprocessor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.inventory.constants.ExceptionMessages;
import com.inventory.constants.InventoryConstants;
import com.inventory.service.InventoryService;

/**
 * after successful parsing. Parser pushes the message to message queue (FIFO).
 * 'n' number of threads pushes the messages to the channelBus at tail.
 */
public class CommandParser {

	private static final boolean verbose=false;

	private static final boolean debug=false;

	private AtomicInteger counter=new AtomicInteger();

	private Map<Integer,String> history=new HashMap<Integer,String>();

	private InventoryService inventoryService=InventoryService.getInstance();

	/**
	 * parser is for parsing and validate the commands
	 * 
	 * @param str
	 * @return
	 */
	public void handleRequest(String str) {

		try {
			if(debug | verbose) {
				System.out.println("command received: "+str);
			}

			String[] filter=str.split(" ");

			if(filter.length<1) {
				System.out.println("Wrong Command");
			}else {
				if(InventoryConstants.commands[0].equalsIgnoreCase(filter[0]) ) {
					if(!isValid(str.length(),4,str)) {
						System.out.println(ExceptionMessages.NOT_ENOUGHT_PARAMETERS);
						return;
					}
					inventoryService.pushMessage(str);

				}else if(InventoryConstants.commands[1].equalsIgnoreCase(filter[0])) {
					if(!isValid(str.length(),2,str)) {
						System.out.println(ExceptionMessages.NOT_ENOUGHT_PARAMETERS);
						return;
					}
					inventoryService.pushMessage(str);
				}else if(InventoryConstants.commands[2].equalsIgnoreCase(filter[0])) {

					if(!isValid(str.length(),3,str)) {
						System.out.println(ExceptionMessages.NOT_ENOUGHT_PARAMETERS);
						return;
					}
					inventoryService.pushMessage(str);
				}else if(InventoryConstants.commands[3].equalsIgnoreCase(filter[0])) {

					if(!isValid(str.length(),3,str)) {
						System.out.println(ExceptionMessages.NOT_ENOUGHT_PARAMETERS);
						return;
					}
					inventoryService.pushMessage(str);
				}else if(InventoryConstants.commands[4].equalsIgnoreCase(filter[0])) {
					if(!isValid(str.length(),1,str)) {
						System.out.println(ExceptionMessages.NOT_ENOUGHT_PARAMETERS);
						return;
					}
					inventoryService.pushMessage(str);
				}else if(InventoryConstants.commands[5].equalsIgnoreCase(filter[0])) {
					if(!isValid(str.length(),3,str)) {
						System.out.println(ExceptionMessages.NOT_ENOUGHT_PARAMETERS);
						return;
					}
					inventoryService.pushMessage(str);
				}else {
					System.out.println("Wrong Command");
				}
			}
		}catch(InterruptedException e1) {
			System.out.println("Interrupted Exception while adding the message to message channe queue: "+e1.getMessage());
		} 
		catch (Exception e2) {
			System.out.println("Unknown Exception while adding the message to message channe queue: "+e2.getMessage());
		}
	}


	private boolean isValid(int noOfParameters,int requiredParameters,String str) {
		if(requiredParameters<=noOfParameters) {
			history.put(counter.getAndIncrement(), str);
			return true;
		}else {
			return false;			
		}
	}
}