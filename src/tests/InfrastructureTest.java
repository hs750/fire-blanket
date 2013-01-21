/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import model.Generator;
import model.Infrastructure;

import org.junit.Before;
import org.junit.Test;

import util.InfoPacket;
import util.Pair;
import util.Pair.Label;

/**
 * @author kw701
 *temp("Temperature"),
		pres("Pressure"),
		coRL("Controll rod level"),
		wLvl("Water level"),
		RPMs("Revolutions per minute"),
		psit("Position"),
		cNme("Name of component"),
		falT("FailuerTime"),
		OPFL("Output flow rate"),
		oPto("Outputs to"),
		rcIF("Recieves input from"),
		elec("Electrisity Generated"),
		cmpnum("Component number"),
 */
public class InfrastructureTest {
	private InfoPacket info;
	private Infrastructure inf1;
	
	/**
	 * Setup an infrastructure to test.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		info = new InfoPacket();
		info.namedValues.add(new Pair<Double>(Label.pres, 100.0));
		info.namedValues.add(new Pair<Double>(Label.coRL, 100.0));
		info.namedValues.add(new Pair<Double>(Label.wLvl, 100.0));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 100.0));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<String>(Label.cNme, "Infrastructure 1"));
		info.namedValues.add(new Pair<Double>(Label.falT, 100.0));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 100.0));
		info.namedValues.add(new Pair<String>(Label.oPto, ""));
		info.namedValues.add(new Pair<String>(Label.rcIF, ""));
		info.namedValues.add(new Pair<Double>(Label.elec, 100.0));
		info.namedValues.add(new Pair<String>(Label.rcIF, ""));
		inf1 = new Infrastructure("Infrastructure 1", info);
	}
	
	/**
	 * Test whether the infrastructure calculates correctly.
	 * That is that it the onfrastructure uses some of the input flow of electrisity and then 
	 */
	@Test
	public void testCalculate() {
		Generator gen = new Generator("Generator 1");
		gen.setOuputFlowRate(100.0);
		
		inf1.connectToInput(gen);
		inf1.setElectrisityneeded(50);
		inf1.calculate();
		
		double inf1Out = inf1.getOutputFlowRate();
		assertTrue(inf1Out == 50);
	}
	
	/**
	 * Test whether the infrastructure takes info correctly
	 */
	@Test
	public void testTakeInfo(){
		InfoPacket i1 = new InfoPacket();
		i1.namedValues.add(new Pair<Double>(Label.elec, 400.0));
		i1.namedValues.add(new Pair<String>(Label.cNme, "Infrastructure 1"));
		try {
			inf1.takeInfo(i1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(inf1.getElectrisityneeded() == 400.0);
	}
	
	/**
	 * Test whether the infrastructure gives the correct info.
	 */
	@Test
	public void testGetInfo(){
		inf1.setElectrisityneeded(100.0);
		InfoPacket inf1I = inf1.getInfo();
		
		assertTrue(inf1I.namedValues.contains(new Pair<String>(Label.cNme, "Infrastructure 1")));
		assertTrue(inf1I.namedValues.contains(new Pair<Double>(Label.elec, 100.0)));
		assertTrue(inf1I.namedValues.contains(new Pair<Double>(Label.OPFL, 100.0)));
	}
}
