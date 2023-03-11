package de.leuphana.swa.messagingsystem.behaviour;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.leuphana.swa.messagingsystem.behaviour.service.MessagingCommandService;


class MessagingSystemTest {

	private static MessagingCommandService messagingSystem;

	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		messagingSystem = new MessagingSystemImpl();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		messagingSystem = null;
	}

	@Test
	void canMessageBeSentViaEmail() {
		Assertions.assertTrue(messagingSystem.deliveryCheck());
	}

}
