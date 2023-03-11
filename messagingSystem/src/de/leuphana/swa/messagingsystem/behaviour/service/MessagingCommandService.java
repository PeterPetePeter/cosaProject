package de.leuphana.swa.messagingsystem.behaviour.service;


public interface MessagingCommandService  {

	public void sendMessage(String date, String header, String body);
	public boolean deliveryCheck();

}