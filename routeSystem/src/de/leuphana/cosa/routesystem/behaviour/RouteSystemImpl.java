package de.leuphana.cosa.routesystem.behaviour;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import de.leuphana.cosa.routesystem.behaviour.service.RouteEventService;
import de.leuphana.cosa.routesystem.behaviour.service.RouteSystemCommandService;
import de.leuphana.cosa.routesystem.structure.Route;

public class RouteSystemImpl implements RouteSystemCommandService, BundleActivator {
	private EventAdmin eventAdministrator;
	private ServiceReference eventAdministratorReference;
	private Set<Route> routes;

	public RouteSystemImpl() {
		this.createStaticRoutes();
	}

	@Override
	public void createStaticRoutes() {
		routes = new HashSet<Route>();

		Route rt1 = new Route();
		rt1.createRoute("Hamburg", "Lueneburg", 24.5f);
		routes.add(rt1);

		Route rt2 = new Route();
		rt2.createRoute("Lueneburg", "Hamburg", 24.5f);
		routes.add(rt2);

		Route rt3 = new Route();
		rt3.createRoute("Hamburg", "Berlin", 311f);
		routes.add(rt3);

		Route rt4 = new Route();
		rt4.createRoute("Berlin", "Hamburg", 311f);
		routes.add(rt4);

		Route rt5 = new Route();
		rt5.createRoute("Berlin", "Lueneburg", 345f);
		routes.add(rt5);

		Route rt6 = new Route();
		rt6.createRoute("Lueneburg", "Berlin", 345f);
		routes.add(rt6);

		Route rt7 = new Route();
		rt7.createRoute("Lueneburg", "Muenchen", 800f);
		routes.add(rt7);

		Route rt8 = new Route();
		rt8.createRoute("Lueneburg", "Paris", 1200f);
		routes.add(rt8);

		Route rt9 = new Route();
		rt9.createRoute("Berlin", "Moskau", 1705f);
		routes.add(rt9);
	}

	@Override
	public Set<Route> getRoutes() {
		return routes;

	}

	
	@Override
	public void getRouteDistance(String start, String end) throws Exception {
		float length;
		Route route = null;
		if (start.equals(end)) {
			throw new Exception("Die Auswahl gleicher Orte ist nicht moeglich!");
		}

		for (Route rt : routes) {
			if (rt == null) {
				throw new Exception("Route nicht vorhanden");
			}
			if (rt.getStart().equals(start) && rt.getDestination().equals(end)) {
				route = rt;
				break;
			}
		}

		if (route != null) {
			length = route.getLength();
			onRouteLengthCalculated(length);
		}

	}

	private void onRouteLengthCalculated(float length) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("length", length);
			Event event = new Event("de/leuphana/ticketautomaton/receive/length", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}

	}

	public void getStartpoints() {
		List<String> listStartpoints = new ArrayList<>();
		for (Route route : routes) {
			listStartpoints.add(route.getStart());
		}
		listStartpoints = listStartpoints.stream().distinct().collect(Collectors.toList());
		onStartpointsCollected(listStartpoints);
	}

	private void onStartpointsCollected(List<String> listStartpoints) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("startpoints", listStartpoints);
			Event event = new Event("de/leuphana/ticketautomaton/receive/startpoints", evtProperties);
			eventAdministrator.sendEvent(event);
		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}

	}

	@Override
	public void getDestinations(String start) {
		List<String> listDestinations = new ArrayList<>();
		for (Route route : routes) {
			if (route.getStart().equals(start)) {
				listDestinations.add(route.getDestination());
			}
		}
		onDestinationsCollected(listDestinations);
	}

	private void onDestinationsCollected(List<String> listDestinations) {
		if (eventAdministrator != null) {
			Map<String, Object> evtProperties = new HashMap<>();
			evtProperties.put("destinations", listDestinations);
			Event event = new Event("de/leuphana/ticketautomaton/receive/destinations", evtProperties);
			eventAdministrator.sendEvent(event);

		} else {
			System.out.println("something went wrong at : " + this.getClass().getName());
		}

	}

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println(this.getClass().getName() + " started");
		String[] eventTopics = new String[] { "de/leuphana/route-system/getstartpoints",
				"de/leuphana/routesystem/getdestinations", "de/leuphana/routesystem/getlength" };
		Dictionary<String, Object> eventHandlerProperties = new Hashtable<>();
		eventHandlerProperties.put(EventConstants.EVENT_TOPIC, eventTopics);
		context.registerService(EventHandler.class.getName(), new RouteEventService(this), eventHandlerProperties);
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
		context.ungetService(eventAdministratorReference);
	}
}
