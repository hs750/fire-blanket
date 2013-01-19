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
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		generator1 = new Generator("Generator 1");
		info = new InfoPacket();
	}

	@After
	public void tearDown() throws Exception {
	}

	private void setUpInfo1(){
		info.namedValues.add(new Pair<String>(Label.cNme, "Generator 1"));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<Double>(Label.elec, 1000.0));
		info.namedValues.add(new Pair<Double>(Label.falT, 20.0));
	}
	@Test
	public void testTakeInfo() {
		setUpInfo1();
		
		try {
			generator1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assert(generator1.getName().equals("Generator 1"));
		assert(generator1.getOutputFlowRate() == 12.34);
		assert(generator1.getElectrisityGenerated() == 1000.0);
		assert(generator1.getFailureTime() == 20.0);
		
	}

	@Test
	public void testGetInfo(){
		setUpInfo1();
		
		assert(info.equals(generator1.getInfo()));
	}
	@Test
	public void testCalc(){
		setUpInfo1();
		try {
			generator1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Turbine t1 = new Turbine("T1");
		InfoPacket tInfo = new InfoPacket();
		tInfo.namedValues.add(new Pair<String>(Label.cNme, "T1"));
		tInfo.namedValues.add(new Pair<Double>(Label.OPFL, 45.0));
		
		try {
			t1.takeInfo(tInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		generator1.connectToInput(t1);
				
		generator1.calculate();
		
		assert(generator1.getElectrisityGenerated() == 1000 + t1.getOutputFlowRate() * 1.5);
	}
}
