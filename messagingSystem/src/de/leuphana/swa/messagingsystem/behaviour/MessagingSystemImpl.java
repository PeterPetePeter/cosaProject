package de.leuphana.swa.messagingsystem.behaviour;

import java.time.LocalDate;
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

import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;
import de.leuphana.swa.messagingsystem.behaviour.service.MessagingEventService;
import de.leuphana.swa.messagingsystem.structure.DeliveryReport;
import de.leuphana.swa.messagingsystem.structure.Sendable;
import de.leuphana.swa.messagingsystem.structure.message.Message;
import de.leuphana.swa.messagingsystem.structure.messagingfactory.AbstractMessagingFactory;
import de.leuphana.swa.messagingsystem.structure.messagingprotocol.MessagingProtocol;


public class MessagingSystemImpl implements MessagingCommandService, BundleActivator {
	private Logger logger;
	private EventAdmin eventAdministrator;
	private ServiceReference eventAdministratorReference;

	@Override
	public void sendMessage(String date, String title, String content) {
		logger = LogManager.getLogger(this.getClass());
		DeliveryReport deliveryReport = new DeliveryReport();
		deliveryReport.setTitle(title);
		deliveryReport.setContent(content);

		
		onDeliveryReportCreated(deliveryReport);

		
	}

	private void onDeliveryReportCreated(DeliveryReport deliveryReport) {
		
		Map<String, Object> evtProperties = new HashMap<>();
		evtProperties.put("date", deliveryReport.getDeliveryDate());
		evtProperties.put("header", deliveryReport.getTitle());
		evtProperties.put("body", deliveryReport.getContent());
		
		Event event = new Event("de/leuphana/messagetoautomaton", evtProperties);
		eventAdministrator.sendEvent(event);
		
	}
	
	public boolean deliveryCheck() {
		return true;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println(this.getClass().getName() + " started");
		String[] eventTopics = new String[] { "de/leuphana/send"};
		Dictionary<String, Object> eventHandlerProperties = new Hashtable<>();
		eventHandlerProperties.put(EventConstants.EVENT_TOPIC, eventTopics);
		context.registerService(EventHandler.class.getName(), new MessagingEventService(this), eventHandlerProperties);
		
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
