package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.Pump;
import anchovy.Pair.Label;

public class PumpTest {
	private Pump pump1;
	private InfoPacket info;
	
	/**
	 * Setup a pump and an infopacket to use in the tests.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		pump1 = new Pump("Pump 1");
		
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Pump 1"));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 100.0));
		
	}

	/**
	 * Test whether a pump correctly takes info.
	 */
	@Test
	public void testTakeInfo() {
		try {
			pump1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(pump1.getRPM() == 100.0);	
	}
	
	/**
	 * Test whether a pump returns the correct info.
	 */
	@Test
	public void testGetInfo(){
		pump1.setRPM(100.0);
		
		assertTrue(pump1.getInfo().namedValues.contains(new Pair<Double>(Label.RPMs, 100.0)));
	}
	
	/**
	 * Test whether the pump correctly calculates.
	 * That is that the pumps output flow rate is correctly calculated.
	 */
	@Test
	public void testCalculate(){
		pump1.setRPM(50.0);
		pump1.calculate();
		
		assertTrue(pump1.getOutputFlowRate() == 50/2);
		
		
	}

}
