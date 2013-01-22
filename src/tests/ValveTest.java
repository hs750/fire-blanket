package tests;

import static org.junit.Assert.*;

import model.Valve;
import org.junit.Before;
import org.junit.Test;


import util.InfoPacket;
import util.Pair;
import util.Pair.Label;


public class ValveTest {
	Valve v1 = null;
	InfoPacket infoTest = null;
	/**
	 * Setup a new valve and info packets to test with.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		v1 = new Valve("Valve 1");
		v1.setFailureTime(100.0);
		v1.setOuputFlowRate(12.3);
		v1.setPosition(true);
		
		infoTest = new InfoPacket();
		infoTest.namedValues.add(new Pair<String> (Label.cNme,"Valve 1"));
		infoTest.namedValues.add(new Pair<Double> (Label.OPFL, 12.3));
		infoTest.namedValues.add(new Pair<Double> (Label.falT, 100.0));
		infoTest.namedValues.add(new Pair<Boolean> (Label.psit, true));
	}

	/**
	 * Test that a valve returns an infoPacket containing the correct info.
	 */
	@Test
	public void testGetInfo() {
		
		InfoPacket v1Info = v1.getInfo();
		assertTrue(v1Info.namedValues.contains(new Pair<String>(Label.cNme, "Valve 1")));
		assertTrue(v1Info.namedValues.contains(new Pair<Boolean>(Label.psit, true)));
		
	}
	/**
	 * Test whether the a valve correctly take info in.
	 */
	@Test
	public void testTakeInfo(){
		assertEquals(true, v1.getPosition());
		
		infoTest.namedValues.clear();
		infoTest.namedValues.add(new Pair<String> (Label.cNme,"Valve 1"));
		infoTest.namedValues.add(new Pair<Boolean> (Label.psit, false));
		
		try {
			v1.takeInfo(infoTest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(false, v1.getPosition());
		
	}
	
	/**
	 * Test that a valve calculates correctly.
	 * That is that it outputs its input flow if open, or 0 if closed.
	 */
	@Test 
	public void testCalculate(){
		Valve v2 = new Valve("Valve 2");
		
		v2.connectToInput(v1);
		v2.connectToOutput(v1);
		v1.connectToInput(v2);
		v1.connectToOutput(v2);
		
		v1.setAmount(0);
		v2.setAmount(50);
		v2.calculate();
		v1.calculate();
		
		assertTrue("" + v1.getOutputFlowRate(), v1.getOutputFlowRate() == 50);;
		
		v1.setPosition(false);
		v1.calculate();
		
		assertTrue(v1.getOutputFlowRate() == 0);
		
	}

}
