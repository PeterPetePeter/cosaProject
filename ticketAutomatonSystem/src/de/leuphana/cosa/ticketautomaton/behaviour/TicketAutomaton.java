package de.leuphana.cosa.ticketautomaton.behaviour;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import de.leuphana.cosa.ticketautomaton.behaviour.service.TicketAutomatonCommandService;
import de.leuphana.cosa.ticketautomaton.behaviour.service.TicketAutomatonEventService;

public class TicketAutomaton implements TicketAutomatonCommandService, BundleActivator {
	private EventAdmin eventAdministrator;
	private ServiceReference eventAdministratorReference;
	public  static BundleContext context;
	private Scanner scanner;
	
	public TicketAutomaton() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
		

		String[] eventTopics = new String[] {  "de/leuphana/ticketautomaton/receive/startpoints",
				"de/leuphana/ticketautomaton/receive/destinations", 
				"de/leuphana/ticketautomaton/receive/tarifs", 
				"de/leuphana/ticketautomaton/receive/length",
				"de/leuphana/ticketautomaton/receive/price", 
				"de/leuphana/messagetoautomaton" };
		Dictionary<String, Object> eventHandlerProperties = new Hashtable<>();
		eventHandlerProperties.put(EventConstants.EVENT_TOPIC, eventTopics);
		System.out.println("Ticket Automat wird gestartet...");
		context.registerService(EventHandler.class.getName(), new TicketAutomatonEventService(this),
				eventHandlerProperties);

		eventAdministratorReference = context.getServiceReference(EventAdmin.class.getName());
		if (eventAdministratorReference != null) {
			eventAdministrator = (EventAdmin) context.getService(eventAdministratorReference);
		} else {
			System.out.println("Administrator not found");
		}
		getRouteStartpoints();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println(this.getClass().getName() + " stopped");
	}

	@Override
	public void getRouteStartpoints(){
		System.out.println("Wilkommen bei der Leuphana Ticketvermittlung! \n");
		System.out.println("\u001B[32m ########## Ticket Automat ########## \u001B[0m");
		System.out.println("Die verfügbaren Routen werden geladen... \n");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("three", "three");
			Event event = new Event("de/leuphana/route-system/getstartpoints", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}

	}

	@Override
	public void getRouteDestinations(String start) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("start", start);
			Event event = new Event("de/leuphana/routesystem/getdestinations", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}

	}

	@Override
	public void getTarifs() {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("three", "three");
			Event event = new Event("de/leuphana/pricingsystem/gettarifs", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}

	}

	@Override
	public void getLength(String start, String end) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("start", start);
			evtProperties.put("end", end);
			Event event = new Event("de/leuphana/routesystem/getlength", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}

	}

	@Override
	public void getPrice(float length, String tarif) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("tarif", tarif);
			evtProperties.put("length", length);
			Event event = new Event("de/leuphana/pricingsystem/getprice", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}
	}

	@Override
	public void sellTicket(String start, String end, float length, float price, String tarif) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("start", start);
			evtProperties.put("end", end);
			evtProperties.put("length", length);
			evtProperties.put("price", price);
			evtProperties.put("tarif", tarif);
			Event event = new Event( "de/leuphana/documentsystem/createticket", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}
	}

	@Override
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
		
	}

	@Override
	public void closeScanner() {
		this.scanner.close();
		
	}

	@Override
	public String scan() {
		return this.scanner.next();
		
	}

	@Override
	public boolean checkStatus() {
		return true;
	}
}