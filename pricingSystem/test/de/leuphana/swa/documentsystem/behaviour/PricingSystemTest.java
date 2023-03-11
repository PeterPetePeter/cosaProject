package de.leuphana.swa.documentsystem.behaviour;

//import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import de.leuphana.cosa.pricingsystem.behaviour.PricingSystemImpl;
import de.leuphana.cosa.pricingsystem.behaviour.service.PricingSystemCommandService;

class PricingSystemTest {

	private static PricingSystemCommandService pricingSystem;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pricingSystem = new PricingSystemImpl();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		pricingSystem = null;
	}

	@Test
	void canPriceBeReturned() {

		//Assert.assertNotNull(pricingSystem.calcPrice(2f));
	}

}
