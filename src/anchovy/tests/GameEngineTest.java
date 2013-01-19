package anchovy.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import anchovy.*;
import anchovy.Components.*;
import anchovy.Pair.Label;

public class GameEngineTest {
	GameEngine gameEngine = null;
	Valve valve1 = null;
	ArrayList<InfoPacket> v1Info = null;
	
	@Before
	public void setUp() throws Exception {
		gameEngine = new GameEngine();
		valve1 = new Valve("Valve 1");
		v1Info = new ArrayList<InfoPacket>();
		gameEngine.addComponent(valve1);
	}

	@Test
	public void testAddComponent() {
		
		ArrayList<InfoPacket> info = gameEngine.getAllComponentInfo();
		v1Info.add(valve1.getInfo());
		assertTrue(v1Info.equals(info));
		
	}
	
	@Test
	public void testConnectComponents(){
		gameEngine.connectComponentTo(valve1, valve1, true);
		
		InfoPacket info = null;
		Iterator<InfoPacket> it = gameEngine.getAllComponentInfo().iterator();
		while(it.hasNext()){
			info = it.next();
			Iterator<Pair<?>> pIt = info.namedValues.iterator();
			while(pIt.hasNext()){
				if(pIt.next().getLabel() == Label.oPto){
					assertTrue(pIt.next().second() == "Valve 1");
				}else if(pIt.next().getLabel() == Label.rcIF){
					assertTrue(pIt.next().second() == "Valve 1");
				}
				
			}
			
			
		}
		
	}
	
	@Test
	public void testAssignInfoToComponent(){
		InfoPacket info = gameEngine.getAllComponentInfo().get(0);
	
		
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		
		try {
			gameEngine.assignInfoToComponent(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Component v1 = gameEngine.getPowerPlantComponent("Valve 1");
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Valve 1"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));;
		info.namedValues.add(new Pair<Double>(Label.falT, v1.getFailureTime()));
		
		InfoPacket v1Info = v1.getInfo();
		assertTrue(v1Info.equals(info));
		
	}
	
	@Test
	public void testSetupPowerPlantConfigureation(){
		ArrayList<InfoPacket> infoList = new ArrayList<InfoPacket>();
		
		InfoPacket info1 = new InfoPacket();
		info1.namedValues.add(new Pair<String>(Label.cNme, "Valve 1"));
		info1.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info1.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info1.namedValues.add(new Pair<String>(Label.rcIF, "Valve 2"));
		info1.namedValues.add(new Pair<String>(Label.oPto, "Valve 2"));
		infoList.add(info1);
		
		InfoPacket info2 = new InfoPacket();
		info2.namedValues.add(new Pair<String>(Label.cNme, "Valve 2"));
		info2.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info2.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info2.namedValues.add(new Pair<String>(Label.oPto, "Valve 1"));
		info2.namedValues.add(new Pair<String>(Label.rcIF, "Valve 1"));
		infoList.add(info2);
		
		gameEngine.clearPowerPlant();
		assertTrue(gameEngine.getAllComponentInfo().isEmpty());
		
		gameEngine.setupPowerPlantConfiguration(infoList);
		info1.namedValues.add(new Pair<Double>(Label.falT, gameEngine.getPowerPlantComponent("Valve 1").getFailureTime()));
		info2.namedValues.add(new Pair<Double>(Label.falT, gameEngine.getPowerPlantComponent("Valve 2").getFailureTime()));
		
		infoList.clear();
		infoList.add(info1);
		infoList.add(info2);
		
		assertTrue(gameEngine.getAllComponentInfo().equals(infoList));	
	}
}
