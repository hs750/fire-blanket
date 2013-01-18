package anchovy.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.Turbine;
import anchovy.Pair.Label;

public class TurbineTest {
	private Turbine turbine1;
	private InfoPacket info;
	@Before
	public void setUp() throws Exception {
		turbine1 = new Turbine("Turbine 1");
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Turbine 1"));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 30.0));
	}

	@Test
	public void testTakeInfo() {
		try {
			turbine1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(turbine1.getRPM() == 30.0);
	}
	

}
