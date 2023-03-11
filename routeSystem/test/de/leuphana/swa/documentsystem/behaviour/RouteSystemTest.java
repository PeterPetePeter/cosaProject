package de.leuphana.swa.documentsystem.behaviour;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.leuphana.cosa.routesystem.behaviour.RouteSystemImpl;
import de.leuphana.cosa.routesystem.structure.Route;

class RouteSystemTest {

	private static RouteSystemImpl routeSystem;
	private static Route route;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		routeSystem = new RouteSystemImpl();
		route = new Route();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		routeSystem = null;
		route = null;
	}

	@Test
	void canRouteLengthBeFound() {
		try {
			Assertions.assertNotNull(routeSystem.getRoutes());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
