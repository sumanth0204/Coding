package com.inventory.entities;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * LinkedMessageQueue works in a FIFO passion 
 * at tail multiple threads updates the multiple messages received by Listener
 * at head single thread reads the messages and performs the update on data source / inventory
 */
public class MessageQueue {
	
	LinkedBlockingQueue<String> channelBus=null;
	
	private static MessageQueue messageQueue=null;
	
	private MessageQueue(){
		channelBus=new LinkedBlockingQueue<String>(); //default size is Integer.MAX_VALUE
	}

	public static synchronized MessageQueue getInstance() {
			if(messageQueue==null) {
				messageQueue=new MessageQueue();
			}
			return messageQueue;
	}	

	public boolean addMessage(String msg) throws InterruptedException {
		channelBus.put(msg);
		return true;
	}

	public String getNextMessage() throws InterruptedException {
		return channelBus.take();
	}
	
	public int getSize() {
		return messageQueue.getSize();
	}
	
	public void display() {
		System.out.println("Messages in channelBus");
		channelBus.forEach((str)->{
			System.out.println(str);
		});
	}
}
