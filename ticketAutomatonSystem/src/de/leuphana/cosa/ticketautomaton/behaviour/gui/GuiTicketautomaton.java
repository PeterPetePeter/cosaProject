package de.leuphana.cosa.ticketautomaton.behaviour.gui;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GuiTicketautomaton {
	private Scanner scanner;
	
	public GuiTicketautomaton(){
		}
		
	public void selectRoute() {
		
	}
	
	public int userSelectionInt() {
		scanner = new Scanner(System.in);
		int selected = scanner.nextInt();
		scanner.close();
		return selected;
	}
	
	public String userSelectionString() {
		scanner = new Scanner(System.in);
		String selected = scanner.next();
		scanner.close();
		return selected;
	}
	
	public void printString(String text) {
		System.out.println(text);
	}
	
	public void printStringList(String eingabeText, List<String> list) {
		System.out.println(eingabeText);
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	public void printMap(String eingabeText, Map<Integer, String> map) {
		System.out.println(eingabeText);
		for(Map.Entry<Integer, String> entry :map.entrySet() ){
			System.out.println(entry.getKey() + ": " + entry.getValue());			
		}
	}
	
	
	
}
