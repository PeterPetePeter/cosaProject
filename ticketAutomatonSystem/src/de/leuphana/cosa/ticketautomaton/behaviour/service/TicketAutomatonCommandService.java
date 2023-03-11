package de.leuphana.cosa.ticketautomaton.behaviour.service;

import java.util.List;
import java.util.Scanner;
import java.util.Set;


public interface TicketAutomatonCommandService {
	public void getRouteStartpoints();
	public void getRouteDestinations(String start);
	public void getTarifs();
	public void getLength(String start, String end);
	public void getPrice(float length, String tarif);
	public void  sellTicket(String start, String end, float length, float price, String tarif);
	public void setScanner(Scanner scanner);
	public void closeScanner();
	public String scan();
	public boolean checkStatus();
	
}
