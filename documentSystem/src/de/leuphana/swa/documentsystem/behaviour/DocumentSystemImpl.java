package de.leuphana.swa.documentsystem.behaviour;

import java.text.DecimalFormat;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;
import de.leuphana.swa.documentsystem.behaviour.service.DocumentEventService;
import de.leuphana.swa.documentsystem.structure.Document;
import de.leuphana.swa.documentsystem.structure.TicketDocumentTemplate;

public class DocumentSystemImpl implements DocumentCommandService, BundleActivator {
	private Map<Integer, Document> documents;
	private Logger logger;
	private EventAdmin eventAdministrator;
	private ServiceReference eventAdministratorReference;

	public DocumentSystemImpl() {
		documents = new HashMap<Integer, Document>();
		logger = LogManager.getLogger(this.getClass());
	}

	@Override
	public void createDocument(String title, String content) {
		Document document = new Document(title);
		document.setText(content);
		addDocument(document);
		onDocumentCreated(document);
	}

	@Override
	public void addDocument(Document document) {
		documents.put(document.getId(), document);
		logger.info("Document with title " + document.getTitel() + " added!");
		
	}

	@Override
	public TicketDocumentTemplate createTicket(String tarif, String start, String end, float length, float price) {
		TicketDocumentTemplate ticket = new TicketDocumentTemplate(tarif, start, end, length, price);
		return ticket;
	}

	@Override
	public void ticketToDocument(TicketDocumentTemplate ticket) {
		DecimalFormat df = new DecimalFormat("0.00");
		String content = "Startbahnhof: ".concat(ticket.getStart()).concat("\nZielbahnhof: ").concat(ticket.getEnd())
				.concat("\nDatum: ").concat(ticket.getDate()).concat("\nStreckenlaenge: ")
				.concat(df.format(ticket.getLength()) + " Kilometer").concat("\nPreis: ")
				.concat(df.format(ticket.getPrice()) + " Euro");
		this.createDocument(ticket.getTarif().concat(" Ticket"), content);
	}

	public void onDocumentCreated(Document doc) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("header", doc.getTitel());
			evtProperties.put("body", doc.getText());

			Event event = new Event("de/leuphana/sendtoprint", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		
		System.out.println(this.getClass().getName() + " started");
		String[] eventTopics = new String[] { "de/leuphana/documentsystem/createticket" };
		Dictionary<String, Object> eventHandlerProperties = new Hashtable<>();
		eventHandlerProperties.put(EventConstants.EVENT_TOPIC, eventTopics);

		context.registerService(EventHandler.class.getName(), new DocumentEventService(this), eventHandlerProperties);

		eventAdministratorReference = context.getServiceReference(EventAdmin.class.getName());
		if (eventAdministratorReference != null) {
			eventAdministrator = (EventAdmin) context.getService(eventAdministratorReference);
		} else {
			System.out.println("Administrator not found");
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println(this.getClass().getName() + " stopped");
	}
}
