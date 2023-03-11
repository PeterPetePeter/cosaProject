package de.leuphana.cosa.ticketautomaton.behaviour.service;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import de.leuphana.cosa.ticketautomaton.behaviour.gui.eingaben.Eingabe;


public class TicketAutomatonEventService implements EventHandler {
	private TicketAutomatonCommandService ticketAutomatonService;

	// Scanner Variablen speichern
	private float price;
	private float length;
	private String tarif;
	private String startTrainstation;
	private String endTrainstation;
	
	
	public static final String rot = "\u001B[31m";
	public static final String gruen = "\u001B[32m";
	public static final String gelb = "\u001B[33m";
	public static final String blau = "\u001B[34m";
	public static final String lila = "\u001B[35m";
	public static final String cry = "\u001B[36m";
	public static final String weiß = "\u001B[37m";
	public static final String noc = "\u001B[0m";

	public TicketAutomatonEventService(TicketAutomatonCommandService service) {
		ticketAutomatonService = service;

	}
	
	public void waiting(Integer sek) {
		try {
			TimeUnit.SECONDS.sleep(sek);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}
	

	@Override
	public void handleEvent(Event event) {
		switch (event.getTopic()) {
		// Startpoints
		case "de/leuphana/ticketautomaton/receive/startpoints":
			List<String> startpoints = (List<String>) event.getProperty("startpoints");
			waiting(2);
			System.out.println("Startbahnhöfe: ");
			for (String string : startpoints) {
				System.out.println(weiß +string);
			}

			ticketAutomatonService.setScanner(new Scanner(System.in));
			String selected = null;
			System.out.println(blau +"Wählen Sie ihren gewüschnten  Startbahnhof aus:  ");
			selected = ticketAutomatonService.scan();
			
			if (selected != null) {
					
					startTrainstation = selected;
					System.out.println(cry + "\n" + selected + " wurde ausgewählt" + noc+ "\n");
					waiting(1);
					ticketAutomatonService.getRouteDestinations(startTrainstation);
				
			}
			break;

		// Destinations
		case "de/leuphana/ticketautomaton/receive/destinations":
			
			List<String> destinations = (List<String>) event.getProperty("destinations");
					
			System.out.println("Zielbahnhöfe: ");
			for (String string : destinations) {
				System.out.println(weiß + string);
			}
			
			System.out.println(blau + "Wählen Sie ihren Zielbahnhof aus:  \n");
			String selected2 = ticketAutomatonService.scan();
			if (destinations != null) {
					endTrainstation = selected2;
					System.out.println(cry + selected2 + " ausgewählt" + noc + "\n");
					ticketAutomatonService.getTarifs();
			}
			
			break;

		// Tarifs
		case "de/leuphana/ticketautomaton/receive/tarifs":
			
			List<String> tarifs = (List<String>) event.getProperty("tarifs");
			waiting(1);
			if (tarifs != null) {
				System.out.println("Preisgruppen: ");
				for (String string : tarifs) {
					System.out.println(weiß + string);
				}
				System.out.println(blau +"Bitte wählen Sie eine Preisgruppe aus den folgenden Möglichkeiten: \n");
//				String selected3 = ticketAutomatonService.scan();
					tarif = "Normal";
					System.out.println(cry + tarif + " ausgewählt"+ noc + "\n");
//					ticketAutomatonService.closeScanner();
					ticketAutomatonService.getLength(startTrainstation, endTrainstation);
			}
			break;

		case "de/leuphana/ticketautomaton/receive/length":
			length = 0f;
			length = (float) event.getProperty("length");
			if (length != 0f) {
				ticketAutomatonService.getPrice(length, tarif);
			}
			break;

		case "de/leuphana/ticketautomaton/receive/price":
			price = 0f;
			price = (float) event.getProperty("price");
			if (price != 0f) {

				System.out.println(blau +"Soll das Ticket gebucht werden (y/n) ? \n" );
				waiting(5);
				System.out.println(weiß +"Buchung wird bestätigt....");
				ticketAutomatonService.sellTicket(startTrainstation, endTrainstation, length, price, tarif);
			}
			break;

		case "de/leuphana/messagetoautomaton":
			String date = (String) event.getProperty("date");
			String title = (String) event.getProperty("header");
			String content = (String) event.getProperty("body");
			if (date != null && title != null) {
				System.out.println("Ihr Ticket...");
				System.out.println(lila + date);
				System.out.println(title);
				System.out.println(content +"\n");
				System.out.println( gruen +"... Bitte entnehmen Sie ihr Ticket aus dem Auotmaten \nGute Fahrt!");
//				TicketAutomaton ticketAutomatonImpl = (TicketAutomaton) ticketAutomatonService;
			} else {
				System.out.println("Ein Fehler ist aufgetreten. Bitte wenden Sie sich an den Support.");
			}

		}
	}

}
