package de.leuphana.swa.documentsystem.structure;

import java.util.Calendar;

public class TicketDocumentTemplate {
	String tarif;
	String start;
	String end;
	float length;
	float price;
	String date;
	
	
	
	public TicketDocumentTemplate(String tarif, String start, String end, float length, float price) {
		super();
		this.tarif = tarif;
		this.start = start;
		this.end = end;
		this.length = length;
		this.price = price;
		this.date = String.valueOf(Calendar.getInstance().DATE);
	}
	public String getTarif() {
		return tarif;
	}
	public void setTarif(String tarif) {
		this.tarif = tarif;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	
	
	
}
