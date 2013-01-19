/**
 * 
 */
package tests;

import static org.junit.Assert.*;
import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.Infrastructure;
import anchovy.Pair.Label;

import org.junit.Before;
import org.junit.Test;

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
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
