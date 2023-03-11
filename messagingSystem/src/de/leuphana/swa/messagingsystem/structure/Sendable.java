package de.leuphana.swa.messagingsystem.structure;

public interface Sendable {
	String getContent();

	MessageType getMessageType();

	String getSender();

	String getReceiver();
}
