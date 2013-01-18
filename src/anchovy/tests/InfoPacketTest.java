package anchovy.tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Pair.Label;

public class InfoPacketTest {
	InfoPacket info = new InfoPacket();
	Pair<?> pair = null;
	String componentName = "Reactor 1";
	
	@Before
	public void setUp() throws Exception {
		info.namedValues.add(new Pair<String>(Label.cNme, componentName));
		info.namedValues.add(new Pair<Double>(Label.temp, 12.34));
		info.namedValues.add(new Pair<Double>(Label.pres, 23.45));
		info.namedValues.add(new Pair<Double>(Label.coRL, 34.56));
		info.namedValues.add(new Pair<Double>(Label.wLvl, 45.67));
	}


	@Test
	public void testName() {
		Iterator<Pair<?>> i = info.namedValues.iterator();
		String s = null;
		Label label = null;
		while(i.hasNext() && s == null){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case cNme:
				s = (String) pair.second();	
				break;
			default:
				s = null;
			}
		}
		assertEquals(componentName, s);
	}
	
	@Test
	public void testAttributes(){
		Iterator<Pair<?>> i = info.namedValues.iterator();
		Double val = null;
		Label label = null;
		Boolean[] attExist = {false,false,false,false};
		while(i.hasNext()){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case temp:
				val = (Double) pair.second();	
				assertEquals("Temperature value was not correct", new Double(12.34), val);
				attExist[0] = true;
				break;
			case pres:
				val = (Double) pair.second();	
				assertEquals("Pressure value was not correct", new Double(23.45), val);
				attExist[1] = true;
				break;
			case coRL:
				val = (Double) pair.second();	
				assertEquals("Control Rod Level was not correct", new Double(34.56), val);
				attExist[2] = true;
				break;
			case wLvl:
				val = (Double) pair.second();
				assertEquals("Water Level did was not correct", new Double(45.67), val);
				attExist[3] = true;
				break;
			case cNme:
				break;
			default:
				fail("There should not have been any other attributes in the list of pairs.");
			}
		}
		Boolean[] attExistTest = {true,true,true,true};
		assertArrayEquals("Not all atributes were found in the list", attExistTest, attExist);
	}
}
