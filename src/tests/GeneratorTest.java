package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.Generator;
import anchovy.Components.Turbine;
import anchovy.Pair.Label;

public class GeneratorTest {
	private Generator generator1;
	private InfoPacket info;

	/**
	 * Before each test, create a new generator and an info packet.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		generator1 = new Generator("Generator 1");
		info = new InfoPacket();
	}

	/**
	 * Put values in the infopacket used in the tests.
	 */
	private void setUpInfo1(){
		info.namedValues.add(new Pair<String>(Label.cNme, "Generator 1"));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<Double>(Label.elec, 1000.0));
		info.namedValues.add(new Pair<Double>(Label.falT, 20.0));
		info.namedValues.add(new Pair<Double>(Label.Amnt, 60.0));
	}
	
	/**
	 * Test whether the Generator correctly takes an infoPacket
	 */
	@Test
	public void testTakeInfo() {
		setUpInfo1();
		
		try {
			generator1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(generator1.getName().equals("Generator 1"));
		assertTrue(generator1.getOutputFlowRate() == 12.34);
		assertTrue(generator1.getElectrisityGenerated() == 1000.0);
		assertTrue(generator1.getFailureTime() == 20.0);
		
	}
	/**
	 * Test whether the generator correctly returns an infopacket.
	 */
	@Test
	public void testGetInfo(){
		generator1.setElectrisityGenerated(1000);
		generator1.setFailureTime(1234.0);
		generator1.setOuputFlowRate(123.0);
		InfoPacket genInfo = generator1.getInfo();
		
		assertTrue(genInfo.namedValues.contains(new Pair<String>(Label.cNme, "Generator 1")));
		assertTrue(genInfo.namedValues.contains(new Pair<Double>(Label.elec, 1000.0)));
		assertTrue(genInfo.namedValues.contains(new Pair<Double>(Label.falT, 1234.0)));
		assertTrue(genInfo.namedValues.contains(new Pair<Double>(Label.OPFL, 123.0)));
	}
	
	/**
	 * Test whether the generator calculates its values correctly
	 */
	@Test
	public void testCalc(){
		setUpInfo1();
		try {
			generator1.takeInfo(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Turbine t1 = new Turbine("T1");
		InfoPacket tInfo = new InfoPacket();
		tInfo.namedValues.add(new Pair<String>(Label.cNme, "T1"));
		tInfo.namedValues.add(new Pair<Double>(Label.OPFL, 45.0));
		
		t1.setRPM(1234.5);
		
		try {
			t1.takeInfo(tInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		generator1.connectToInput(t1);
				
		generator1.calculate();
		
		assertTrue(generator1.getElectrisityGenerated() == 1000 + t1.getRPM() * 1.5);
	}
}
