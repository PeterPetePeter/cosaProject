package de.leuphana.swa.printingsystem.behaviour.service;


import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class PrintEventService implements EventHandler{
	private PrintingCommandService printService;
	
	public PrintEventService(PrintingCommandService service) {
		printService = service;
	}
	
	@Override
	public void handleEvent(Event event) {
	
		if (event.getTopic().equals("de/leuphana/sendtoprint")) {
			String title = (String) event.getProperty("header");
			if(title != null) {
			String content = (String) event.getProperty("body");
			printService.print(title, content);
			}
			
		}
		
	}
	
}