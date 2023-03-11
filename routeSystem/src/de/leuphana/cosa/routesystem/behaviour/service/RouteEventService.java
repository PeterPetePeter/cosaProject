package de.leuphana.cosa.routesystem.behaviour.service;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class RouteEventService implements EventHandler {
	RouteSystemCommandService routeService;

	public RouteEventService(RouteSystemCommandService service) {
		routeService = service;
	}

	@Override
	public void handleEvent(Event event) {

		switch (event.getTopic()) {
		case "de/leuphana/route-system/getstartpoints":
			String empty = (String) event.getProperty("three");
			if(empty != null) {
			routeService.getStartpoints();
			}
			break;

		case "de/leuphana/routesystem/getdestinations":
			String start = (String) event.getProperty("start");
			if (start != null) {
				routeService.getDestinations(start);
			}
			break;

		case "de/leuphana/routesystem/getlength":
			String start1 = (String) event.getProperty("start");
			String end1 = (String) event.getProperty("end");
			try {
				routeService.getRouteDistance(start1, end1);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			break;
		}

	}
}
