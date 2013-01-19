package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.Reactor;
import anchovy.Components.Valve;
import anchovy.Pair.Label;

public class ReactorTest {
	private Reactor reactor1;
	private InfoPacket info;
	
	@Before
	public void setUp() throws Exception {
		reactor1 = new Reactor("Reactor 1");
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Reactor 1"));
		info.namedValues.add(new Pair<Double>(Label.temp, 50.0));
		info.namedValues.add(new Pair<Double>(Label.pres, 100.0));
		info.namedValues.add(new Pair<Double>(Label.coRL, 60.0));
		
	}
	@Test
	public void testTakeInfo() {
		try {
			reactor1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert(reactor1.getPressure() == 100.0);
		assert(reactor1.getTemperature() == 50.0);
		assert(reactor1.getControlRodLevel() == 60.0);
		
	}
	
	@Test
	public void testGetInfo(){
		reactor1.setPressure(100.0);
		reactor1.setTemperature(50.0) ;
		reactor1.setControlRodLevel(60.0) ;
		
		info.namedValues.add(new Pair<Double>(Label.wLvl, 50.0));
		assert(reactor1.getInfo().equals(info));
	}
	
	@Test
	public void testCalculate(){
		reactor1.setOuputFlowRate(40);
		reactor1.calculate();
		
		Valve v1 = new Valve("Valve 1");
		v1.setOuputFlowRate(30);
		reactor1.connectToInput(v1);
		v1.connectToOutput(reactor1);
		
		assert(reactor1.getWaterLevel() != 50.0);
		assert(reactor1.getTemperature() != 50.0);
		assert(reactor1.getPressure() != 100.0);
	}

}
