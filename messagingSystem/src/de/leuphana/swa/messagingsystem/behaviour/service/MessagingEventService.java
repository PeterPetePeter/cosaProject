package de.leuphana.swa.messagingsystem.behaviour.service;

 

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class MessagingEventService implements EventHandler {
	private MessagingCommandService messagingService;
	
	
	public MessagingEventService(MessagingCommandService service) {
		messagingService = service;
	}


	@Override
	public void handleEvent(Event event) {
		if(event.getTopic().equals("de/leuphana/send")){
			
			String date = (String) event.getProperty("date");
			String title = (String) event.getProperty("header");
			String content = (String) event.getProperty("body");
			if(date != null) {			
			messagingService.sendMessage(date, title, content);
			}
		}
		
	}
	
	
	
	
	
}
