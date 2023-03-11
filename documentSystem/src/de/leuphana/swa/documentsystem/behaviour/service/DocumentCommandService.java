package de.leuphana.swa.documentsystem.behaviour.service;

import de.leuphana.swa.documentsystem.structure.Document;
import de.leuphana.swa.documentsystem.structure.TicketDocumentTemplate;

public interface DocumentCommandService  {

	void createDocument(String title, String content);
	void addDocument(Document document);
	//TODO to implement
//	void updateDocument(Document document);
//	Document getDocument(Integer documentId) throws DocumentNotFoundException;
	TicketDocumentTemplate createTicket(String tarif, String start, String end, float length, float price);
	void ticketToDocument(TicketDocumentTemplate ticket);

}