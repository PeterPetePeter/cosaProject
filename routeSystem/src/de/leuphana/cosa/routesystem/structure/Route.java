package de.leuphana.cosa.routesystem.structure;

public class Route {
	private String start;
	private String destination;
	private float length;
	
	public Route(){
	}

	public void createRoute(String start, String destination, float length) {
		if(start == null || destination == null) {
			System.out.println("Wert einer Route ist null");
		}
		this.start = start;
		this.destination = destination;
		this.length = length * 1.45f;
	}
	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}
	
}
