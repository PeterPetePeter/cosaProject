package de.leuphana.cosa.routesystem.behaviour.service;


import java.util.Set;
import de.leuphana.cosa.routesystem.structure.Route;

public interface RouteSystemCommandService {
	
	public void getStartpoints();
	public void	getDestinations(String start);
	public void getRouteDistance(String start, String destination) throws Exception;
	public Set<Route> getRoutes();
	public void createStaticRoutes();
}
