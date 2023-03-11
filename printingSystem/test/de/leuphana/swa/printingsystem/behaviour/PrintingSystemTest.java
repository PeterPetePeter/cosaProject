package de.leuphana.swa.printingsystem.behaviour;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import de.leuphana.swa.printingsystem.behaviour.service.PrintingCommandService;



class PrintingSystemTest {

	private static PrintingCommandService printingSystem;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		printingSystem = new PrintingSystemImpl();

		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		printingSystem = null;
	}

	@Test
	void canDocumentBePrinted() {
		Assertions.assertNotNull(printingSystem.printReportCheck());
	}

}
