package tests;

import static org.junit.Assert.*;

import controller.*;
import model.*;
import util.*;
import view.*;

import org.junit.Before;
import org.junit.Test;

public class RequirementsTests {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * While the turbine is turning the generator, the total amount of power produced by the power plant will increase proportionally to the amount of steam flowing round the system. 
	 * Test implemented by creating a very simple power plant configuration of a valve, turbine and generator.
	 * The valves output flow rate is set to 100 then the turbine and generator calculate(), getting the initial value of the electricity being output.
	 * The valves output flow rate is then set to 200 and calculates are preformed again. 
	 * Then it is asserted that the output from the generator increased (due to the flow rate entering the turbine increasing thus increasing its RPM, thus generating more electricity). 
	 * 
	 */
	@Test
	public void TU01_SF08() {
		Turbine turbine = new Turbine("Turbine");
		Generator generator = new Generator("Generator");
		Valve valve = new Valve("Valve");
		
		valve.connectToOutput(turbine);
		turbine.connectToInput(valve);
		turbine.connectToOutput(generator);
		turbine.connectToOutput(valve);
		generator.connectToInput(turbine);
		
		valve.setOuputFlowRate(100);
		
		turbine.calculate();
		generator.calculate();
		
		Double outputOfGenerator = generator.getOutputFlowRate();
		
		valve.setOuputFlowRate(200);
		
		turbine.calculate();
		generator.calculate();
		
		assertTrue("Original: " + outputOfGenerator + ". New: " + generator.getOutputFlowRate(), outputOfGenerator < generator.getOutputFlowRate());
	}

}
