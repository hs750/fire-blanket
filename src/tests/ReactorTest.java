package tests;

import static org.junit.Assert.*;

import model.Reactor;
import model.Valve;

import org.junit.Before;
import org.junit.Test;

import util.InfoPacket;
import util.Pair;
import util.Pair.Label;


public class ReactorTest {
	private Reactor reactor1;
	private InfoPacket info;
	
	/**
	 * Setup a new reactor for each test and an infopacket to use with it.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		reactor1 = new Reactor("Reactor 1");
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Reactor 1"));
		info.namedValues.add(new Pair<Double>(Label.temp, 50.0));
		info.namedValues.add(new Pair<Double>(Label.coRL, 60.0));
		
	}
	/**
	 * Test whether a reactor takes info correctly.
	 */
	@Test
	public void testTakeInfo() {
		try {
			reactor1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(reactor1.getTemperature() == 50.0);
		assertTrue(reactor1.getControlRodLevel() == 60.0);
		
	}
	/**
	 * Test whether the reactor returns the correct info about itself
	 */
	@Test
	public void testGetInfo(){
		reactor1.setTemperature(50.0) ;
		reactor1.setControlRodLevel(60.0) ;
		
		info.namedValues.add(new Pair<Double>(Label.wLvl, 50.0));
		assertTrue(reactor1.getInfo().namedValues.contains(new Pair<Double>(Label.pres, (reactor1.getAmount()* reactor1.getTemperature())/reactor1.getVolume())));
		assertTrue(reactor1.getInfo().namedValues.contains(new Pair<Double>(Label.temp, 50.0)));
		assertTrue(reactor1.getInfo().namedValues.contains(new Pair<Double>(Label.coRL, 60.0)));
		assertTrue(reactor1.getInfo().namedValues.contains(new Pair<String>(Label.cNme, "Reactor 1")));
	}
	
	/**
	 * Test whether a reactor calculates its values correctly.
	 * That is that its values changes at every iteration of the control loop.
	 */
	@Test
	public void testCalculate(){
		reactor1.setOuputFlowRate(40);
		reactor1.calculate();
		
		Valve v1 = new Valve("Valve 1");
		v1.setOuputFlowRate(30);
		reactor1.connectToInput(v1);
		v1.connectToOutput(reactor1);
		
		assertTrue(reactor1.getWaterLevel() != 50.0);
		assertTrue(reactor1.getTemperature() != 50.0);
		assertTrue(reactor1.getPressure() != 100.0);
	}
	

}
