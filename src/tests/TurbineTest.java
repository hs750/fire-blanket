package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.Pump;
import anchovy.Components.Turbine;
import anchovy.Pair.Label;

public class TurbineTest {
	private Turbine turbine1;
	private InfoPacket info;
	
	/**
	 * Setup a new turbine and an info packet for use in the tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		turbine1 = new Turbine("Turbine 1");
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Turbine 1"));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 30.0));
	}

	/**
	 * Test whether a turbine takes info correctly
	 */
	@Test
	public void testTakeInfo() {
		try {
			turbine1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(turbine1.getRPM() == 30.0);
	}
	
	/**
	 * Test whether a turbine correctly returns its info.
	 */
	@Test
	public void testGetInfo(){
		turbine1.setRPM(200.0);
		
		InfoPacket t1I = turbine1.getInfo();
		assertTrue(t1I.namedValues.contains(new Pair<Double>(Label.RPMs, 200.0)));
		assertTrue(t1I.namedValues.contains(new Pair<String>(Label.cNme, "Turbine 1")));
	}
	
	/**
	 * Test whether the turbine calculates correctly.
	 */
	@Test
	public void testCalculate(){
		Pump p = new Pump("Pump 1");
		p.setOuputFlowRate(100);
		
		turbine1.connectToInput(p);
		turbine1.calculate();
		
		assertTrue(turbine1.getRPM() == p.getOutputFlowRate()/turbine1.getRPMRatio());
		assertTrue(turbine1.getOutputFlowRate() == p.getOutputFlowRate());
		
	}

}
