package tests;

import static org.junit.Assert.*;

import model.Generator;

import org.junit.Before;
import org.junit.Test;

import util.InfoPacket;
import util.Pair;
import util.Pair.Label;


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
	 * @deprecated Thus is now done by a requirements test case. 
	 */
	@Test
	public void testCalc(){
//		setUpInfo1();
//		try {
//			generator1.takeInfo(info);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Valve v1 = new Valve("Valve");
//		Turbine t1 = new Turbine("T1");
//		
//		t1.connectToInput(v1);
//		t1.connectToOutput(v1);
//		v1.connectToInput(t1);
//		v1.connectToOutput(t1);
//		v1.setOuputFlowRate(50);
//		
//		
//		
//		generator1.connectToInput(t1);
//		t1.connectToOutput(generator1);
//		t1.calculate();
//		generator1.calculate();
//
//		Double elecGened = generator1.getElectrisityGenerated();
//		Double gOPFL = generator1.getOutputFlowRate();
//		
//		v1.setOuputFlowRate(60);
//		t1.calculate();
//		generator1.calculate();
//		
//		Double gOPFL2 = generator1.getOutputFlowRate();
//		Double elecGened2 = generator1.getElectrisityGenerated();
//		
//		assertTrue(gOPFL < gOPFL2);
//		assertTrue(elecGened < elecGened2);
		
		
	}
}
