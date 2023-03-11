package de.leuphana.swa.printingsystem.behaviour;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
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

import de.leuphana.swa.printingsystem.behaviour.service.PrintEventService;
import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;
import de.leuphana.swa.printingsystem.structure.PrintFormat;
import de.leuphana.swa.printingsystem.structure.PrintJob;
import de.leuphana.swa.printingsystem.structure.PrintJobQueue;
import de.leuphana.swa.printingsystem.structure.PrintReport;
import de.leuphana.swa.printingsystem.structure.Printable;
import de.leuphana.swa.printingsystem.structure.Printer;

public class PrintingSystemImpl implements PrintingCommandService, BundleActivator {
	private PrintJobQueue printJobQueue;
	private EventAdmin eventAdministrator;
	private ServiceReference eventAdministratorReference;
	private Logger logger;

	public PrintingSystemImpl() {
		printJobQueue = PrintJobQueue.getInstance();
		printJobQueue.addPrinter(new Printer(PrintFormat.A4));
		printJobQueue.addPrinter(new Printer(PrintFormat.A3));
	}

	@Override
	public void print(String title, String content) {
		Printable printable = new Printable() {
			@Override
			public String getTitle() {
				return title;
			}

			@Override
			public String getContent() {
				return content;
			}
		};

		PrintJob printJob = new PrintJob(printable);

		System.out.println("Ticket wird gedruckt... \nBitte warten Sie einen Augenblick.\n");

		printJobQueue.addPrintJob(printJob);

		PrintReport printReport = new PrintReport();
		printReport.setTitle(title);
		printReport.setContent(content);
		logger = LogManager.getLogger(this.getClass());
		String log =  title + ", " + content;
		logger.info(log);
		onPrintReportCreated(printReport);
	}

	private void onPrintReportCreated(PrintReport printReport) {
		Map<String, Object> evtProperties = new HashMap<>();
		evtProperties.put("date", printReport.getPrintDate());
		evtProperties.put("header", printReport.getTitle());
		evtProperties.put("body", printReport.getContent());

		Event event = new Event("de/leuphana/send", evtProperties);
		eventAdministrator.sendEvent(event);

	}

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println(this.getClass().getName() + " started");

		String[] eventTopics = new String[] { "de/leuphana/sendtoprint" };
		Dictionary<String, Object> eventHandlerProperties = new Hashtable<>();
		eventHandlerProperties.put(EventConstants.EVENT_TOPIC, eventTopics);

		context.registerService(EventHandler.class.getName(), new PrintEventService(this), eventHandlerProperties);

		eventAdministratorReference = context.getServiceReference(EventAdmin.class.getName());

		if (eventAdministratorReference != null) {
			// TODO
			eventAdministrator = (EventAdmin) context.getService(eventAdministratorReference);
		} else {
			System.out.println("Administrator not found");
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println(this.getClass().getName() + " stopped");
	}

	public boolean printReportCheck() {
		return true;
	}
}
