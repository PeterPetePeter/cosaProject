package de.leuphana.cosa.ticketautomaton.behaviour.gui.eingaben;

import java.util.Scanner;

public class Eingabe {
	private Scanner scanner;
	
	public String scan() {
		return scanner.next();
	}
	
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	public void closScanner() {
		this.scanner.close();
	}
}
