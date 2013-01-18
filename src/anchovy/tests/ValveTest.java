package anchovy.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import anchovy.*;
import anchovy.Components.Valve;
import anchovy.Pair.Label;

public class ValveTest {
	Valve v1 = null;
	InfoPacket infoTest = null;
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

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInfo() {
		
		InfoPacket v1Info = v1.getInfo();
		assert(v1Info.equals(infoTest));
		
	}
	
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
		
		infoTest.namedValues.add(new Pair<Double> (Label.OPFL, 12.3));
		infoTest.namedValues.add(new Pair<Double> (Label.falT, 100.0));
		assert(v1.getInfo().equals(infoTest));
		
		assertEquals(false, v1.getPosition());
		
	}
	
	@Test 
	public void testCalculate(){
		Valve v2 = new Valve("Valve 2");
		v2.setOuputFlowRate(200);
		v1.connectToInput(v2);
		
		assert(v1.getOutputFlowRate() == 100.0);
		
		v1.calculate();
		
		assert(v1.getOutputFlowRate() == 200.0);
		
		v1.setPosition(false);
		v1.calculate();
		
		assert(v1.getOutputFlowRate() == 0);
		
	}

}
