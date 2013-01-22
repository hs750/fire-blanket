package tests;

import static org.junit.Assert.*;
import model.Pump;
import model.Turbine;

import org.junit.Before;
import org.junit.Test;

import util.InfoPacket;
import util.Pair;
import util.Pair.Label;


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
		info.namedValues.add(new Pair<Double>(Label.OPFL, 30.0));
	}

	/**
	 * Test whether a turbine takes info correctly
	 */
	@Test
	public void testTakeInfo() {
		try {
			turbine1.takeInfo(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(turbine1.getRPM() == turbine1.getOutputFlowRate() * turbine1.getRPMRatio());
	}
	
	/**
	 * Test whether a turbine correctly returns its info.
	 */
	@Test
	public void testGetInfo(){ //TODO make this work
		//turbine1.setRPM(200.0);
		
		InfoPacket t1I = turbine1.getInfo();
		assertTrue(t1I.namedValues.contains(new Pair<Double>(Label.OPFL, turbine1.getOutputFlowRate())));
		assertTrue(t1I.namedValues.contains(new Pair<String>(Label.cNme, "Turbine 1")));
	}
	
	/**
	 * Test whether the turbine calculates correctly.
	 */
	@Test
	public void testCalculate(){
		Pump p = new Pump("Pump 1");
		p.setAmount(50);
		
		p.connectToInput(p);
		p.connectToOutput(turbine1);
		
		turbine1.connectToInput(p);
		turbine1.connectToOutput(p);
		turbine1.setAmount(50);
		
		p.calculate();
		turbine1.calculate();
		
		assertTrue(turbine1.getRPM() == p.getOutputFlowRate()/turbine1.getRPMRatio());
		assertTrue(turbine1.getOutputFlowRate() == p.getOutputFlowRate());
		
	}

}
