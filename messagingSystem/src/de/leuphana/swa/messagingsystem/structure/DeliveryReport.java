package de.leuphana.swa.messagingsystem.structure;

import java.time.LocalDate;

public class DeliveryReport  {
	
	private String deliveryDate;
	private String title;
	private String content;
	
	public  DeliveryReport() {
		deliveryDate = LocalDate.now().toString();
		
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String conetent) {
		this.content = conetent;
	}

	

	public String getDeliveryDate() {
		return deliveryDate;
	}
	
	

}
