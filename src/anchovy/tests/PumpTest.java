package anchovy.tests;

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
	

	@Before
	public void setUp() throws Exception {
		pump1 = new Pump("Pump 1");
		
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Pump 1"));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 100.0));
		
	}


	@Test
	public void testTakeInfo() {
		try {
			pump1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assert(pump1.getRPM() == 100.0);	
	}
	
	@Test
	public void testGetInfo(){
		pump1.setRPM(100.0);
		assert(info.equals(pump1.getInfo()));
	}
	
	@Test
	public void testCalculate(){
		pump1.setRPM(50.0);
		pump1.calculate();
		
		assert(pump1.getOutputFlowRate() == 50/2);
		
		
	}

}
