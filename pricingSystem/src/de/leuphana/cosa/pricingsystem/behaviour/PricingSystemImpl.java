package de.leuphana.cosa.pricingsystem.behaviour;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import de.leuphana.cosa.pricingsystem.behaviour.service.PricingEventService;
import de.leuphana.cosa.pricingsystem.behaviour.service.PricingSystemCommandService;

public class PricingSystemImpl implements BundleActivator, PricingSystemCommandService {
	private EventAdmin eventAdministrator;
	private ServiceReference eventAdministratorReference;

	public PricingSystemImpl() {
	}

	@Override
	public void getPrice(String tarif, float length) throws Exception {
		float price;
		if (tarif.equals(SCHNAEPPCHEN_TARIF)) {
			price = calcPrice(length) * 0.5f;
		} else if (tarif.equals(GUENSTIGER_REISEN_TARIF)) {
			price = calcPrice(length) * 0.75f;
		} else if (tarif.equals(NORMAL_TARIF)) {
			price = calcPrice(length);
		} else {
			throw new Exception(tarif + ": ist kein valider Tarif.");
		}
		
		onPriceCalculated(price);
	}

	public float calcPrice(float len) {

		return len * 0.03f;
	}
	
	private void onPriceCalculated(float price) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("price", price);
			Event event = new Event("de/leuphana/ticketautomaton/receive/price", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}
	}

	@Override
	public void getTarifs() {
		List<String> listTarifs = new ArrayList<>();
		listTarifs.add(this.GUENSTIGER_REISEN_TARIF);
		listTarifs.add(this.NORMAL_TARIF);
		listTarifs.add(this.SCHNAEPPCHEN_TARIF);
		onTarifListCreated(listTarifs);
	}
	
	private void onTarifListCreated(List<String> listTarifs) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("tarifs", listTarifs);
			Event event = new Event("de/leuphana/ticketautomaton/receive/tarifs", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}
	}

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println(this.getClass().getName() + " started");
		String[] eventTopics = new String[] { "de/leuphana/pricingsystem/getprice", 
				"de/leuphana/pricingsystem/gettarifs" };
		Dictionary<String, Object> eventHandlerProperties = new Hashtable<>();
		eventHandlerProperties.put(EventConstants.EVENT_TOPIC, eventTopics);
		context.registerService(EventHandler.class.getName(), new PricingEventService(this), eventHandlerProperties);
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
