package de.leuphana.swa.documentsystem.behaviour.service;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import de.leuphana.swa.documentsystem.structure.TicketDocumentTemplate;

public class DocumentEventService implements EventHandler {
	DocumentCommandService docService;

	public DocumentEventService(DocumentCommandService service) {
		docService = service;
	}

	@Override
	public void handleEvent(Event event) {

		if (event.getTopic().equals("de/leuphana/documentsystem/createticket")) {
			String start = (String) event.getProperty("start");
			if (start != null) {
				String end = (String) event.getProperty("end");
				float length = (float) event.getProperty("length");
				float price = (float) event.getProperty("price");
				String tarif = (String) event.getProperty("tarif");
				TicketDocumentTemplate ticket = docService.createTicket(tarif, start, end, length, price);
				docService.ticketToDocument(ticket);
			}
		}

	}

}
