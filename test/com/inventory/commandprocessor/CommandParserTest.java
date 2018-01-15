package com.inventory.commandprocessor;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.inventory.constants.InventoryConstants;
import com.inventory.datasource.DBAccess;
import com.inventory.reports.Report;
import com.inventory.service.InventoryService;


public class CommandParserTest {
	
	private static boolean verbose=true;
	
	static {
		//Application.main(null);
	}
	
	public static void main(String[] args) {
		try {
			if(InventoryConstants.commands[0].equalsIgnoreCase("create"))
				System.out.println(InventoryConstants.commands[0]);
		} catch (Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
	}
	
	
	@Test
	public void testDBAccess() {
			DBAccess dbAccess=DBAccess.getInstance();
			assertNotNull(dbAccess);
			if(verbose) System.out.println("teseCase Passed: testDBAccess");
	}	

	@Test
	public void testHandleRequest() {
		
		CommandParser parser=new CommandParser();
		Report report=Report.getInstance();
		assertNotNull(parser);
		assertNotNull(report);
		
		List<String> commands1=getCommands1();
		List<String> commands2=getCommands2();

		long startTime=System.currentTimeMillis();
		
		CompletableFuture<Void> future
		= CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				if(verbose) System.out.println("Initiating the message Handler");
				InventoryService.getInstance().messageHandler();
			}				  
		});
		
		commands1.forEach((str)->{
			parser.handleRequest(str);
		});
		System.out.println("elapsed Time: "+(System.currentTimeMillis()-startTime));
		//report.printReport();
		
		startTime=System.currentTimeMillis();
		commands2.forEach((str)->{
			parser.handleRequest(str);
		});
		System.out.println("elapsed Time: "+(System.currentTimeMillis()-startTime));
		//report.printReport();
		if(verbose) System.out.println("teseCase Passed: testHandleRequest");
		
		try {
			//make sure wait for completion of Future Object
			future.get();
			/*ExecutorService executor=Executors.newSingleThreadExecutor();
			executor.awaitTermination(300, TimeUnit.SECONDS);*/
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	private static List<String> getCommands2(){
		List<String> commandList=new ArrayList<String>();
		
		commandList.add("delete Book01");
		commandList.add("updateSell Tab01 5");
		commandList.add("create Mobile01 10.51 44.56");
		
		commandList.add("updateBuy Mobile01 250");
		commandList.add("updateSell Food01 5");
		commandList.add("updateSell Mobile01 4");
		commandList.add("updateSell Med01 10");
		commandList.add("report");
		return commandList;
	}
	
	private static List<String> getCommands1(){
		List<String> commandList=new ArrayList<String>();
		commandList.add("create Book01 10.50 13.79");
		commandList.add("create Food01 1.47 3.98");
		commandList.add("create Med01 30.63 34.29");
		commandList.add("create Tab01 57.00 84.98");
		
		commandList.add("updateBuy Tab01 100");
		commandList.add("updateSell Tab01 2");
		commandList.add("updateBuy Food01 500");
		commandList.add("updateBuy Book01 100");
		commandList.add("updateBuy Med01 100");
		
		commandList.add("updateSell Food01 1");
		commandList.add("updateSell Food01 1");
		commandList.add("updateSell Tab01 2");
		commandList.add("report");
		return commandList;
	}
}