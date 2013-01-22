package tests;

import static org.junit.Assert.*;

import model.Condenser;
import model.Valve;


import org.junit.Before;

import org.junit.Test;

import util.InfoPacket;
import util.Pair;
import util.Pair.Label;


public class CondenserTest {
	private Condenser condensor1;
	private InfoPacket info;
	
	/**
	 * Setup a new reactor for each test and an infopacket to use with it.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		condensor1 = new Condenser("Condensor 1");
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Condensor 1"));
		info.namedValues.add(new Pair<Double>(Label.temp, 50.0));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 60.0));
		
	}
	/**
	 * Test whether a reactor takes info correctly.
	 */
	@Test
	public void testTakeInfo() {
		try {
			condensor1.takeInfo(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(condensor1.getTemperature() == 50.0);
		assertTrue(condensor1.getCoolantpumpRPM() == 60.0);
		
	}
	/**
	 * Test whether the reactor returns the correct info about itself
	 */
	@Test
	public void testGetInfo(){
		condensor1.setTemperature(50.0) ;
		condensor1.setCoolantpumpRPM(60.0) ;
		
		info.namedValues.add(new Pair<Double>(Label.wLvl, 50.0));
		assertTrue(condensor1.getInfo().namedValues.contains(new Pair<Double>(Label.pres, (condensor1.getAmount()* condensor1.getTemperature())/condensor1.getVolume())));
		assertTrue(condensor1.getInfo().namedValues.contains(new Pair<Double>(Label.temp, 50.0)));
		assertTrue(condensor1.getInfo().namedValues.contains(new Pair<Double>(Label.RPMs, 60.0)));
		assertTrue(condensor1.getInfo().namedValues.contains(new Pair<String>(Label.cNme, "Condensor 1")));
	}
	
	/**
	 * Test whether a reactor calculates its values correctly.
	 * That is that its values changes at every iteration of the control loop.
	 */
	@Test
	public void testCalculate(){
		condensor1.setOuputFlowRate(40);
		condensor1.calculate();
		
		Valve v1 = new Valve("Valve 1");
		v1.setOuputFlowRate(30);
		condensor1.connectToInput(v1);
		condensor1.connectToOutput(v1);
		v1.connectToInput(condensor1);
		v1.connectToOutput(condensor1);
		
		assertTrue(condensor1.getTemperature() != 50.0);
		assertTrue(condensor1.getPressure() != 100.0);
	}
	

}
