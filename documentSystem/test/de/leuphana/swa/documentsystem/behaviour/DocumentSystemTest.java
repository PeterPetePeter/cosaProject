package de.leuphana.swa.documentsystem.behaviour;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import de.leuphana.swa.documentsystem.behaviour.service.DocumentCommandService;


class DocumentSystemTest {

	private static DocumentCommandService documentSystem;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		documentSystem = new DocumentSystemImpl();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		documentSystem = null;
	}

	@Test
	void canDocumentBeAdded() {
		Assertions.assertNotNull(documentSystem.createTicket("Normal", "start", "end", 1.0f, 2.0f));
	}

}
