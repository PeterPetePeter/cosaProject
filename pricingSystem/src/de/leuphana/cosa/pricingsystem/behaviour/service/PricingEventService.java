package de.leuphana.cosa.pricingsystem.behaviour.service;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class PricingEventService implements EventHandler {
	PricingSystemCommandService pricingService;

	public PricingEventService(PricingSystemCommandService service) {
		pricingService = service;
	}

	@Override
	public void handleEvent(Event event) {
		switch (event.getTopic()) {
		case "de/leuphana/pricingsystem/gettarifs":
			String empty = (String) event.getProperty("three");
			if(empty.equals("three")) {
			pricingService.getTarifs();
			}
			break;

		case "de/leuphana/pricingsystem/getprice":
			String tarif = (String) event.getProperty("tarif");
			float length = 0f;
			length = (float) event.getProperty("length");
			if (length != 0f) {
				try {
					pricingService.getPrice(tarif, length);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			break;
		}
	}
}
