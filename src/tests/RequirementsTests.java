package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import controller.*;
import model.*;
import util.*;
import util.Pair.Label;
import view.*;

import org.junit.Before;
import org.junit.Test;

public class RequirementsTests {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * While the turbine is turning the generator, the total amount of power produced by the power plant will increase proportionally to the amount of steam flowing round the system. 
	 * Test implemented by creating a very simple power plant configuration of a valve, turbine and generator.
	 * The valves output flow rate is set to 100 then the turbine and generator calculate(), getting the initial value of the electricity being output.
	 * The valves output flow rate is then set to 200 and calculates are performed again. 
	 * Then it is asserted that the output from the generator increased (due to the flow rate entering the turbine increasing thus increasing its RPM, thus generating more electricity). 
	 * 
	 */
	@Test
	public void TU32_SF08() {
		Turbine turbine = new Turbine("Turbine");
		Generator generator = new Generator("Generator");
		Valve valve = new Valve("Valve");
		
		valve.connectToInput(turbine);
		valve.connectToOutput(turbine);
		turbine.connectToInput(valve);
		turbine.connectToOutput(generator);
		turbine.connectToOutput(valve);
		generator.connectToInput(turbine);
		
		valve.setAmount(20);
		turbine.setAmount(50);
		
		valve.calculate();
		
		turbine.calculate();
		generator.calculate();
		
		
		Double outputOfGenerator = generator.getOutputFlowRate();
		
		valve.setAmount(40);
		turbine.setAmount(100);
		turbine.calculate();
		generator.calculate();
		
		
		assertTrue("Original: " + outputOfGenerator + ". New: " + generator.getOutputFlowRate(), outputOfGenerator < generator.getOutputFlowRate());
	}
	/**
	 * The hotter the reactor tank is, the higher the rate of steam production. No steam will be produced while the reactor temperature is below 100 degrees Celsius.
	 * Creates a basic power plant containing just a valve and a reactor. 
	 * The valves output flow rate is set to 50 so that the Reactor has something to use to calculate with. And other reactor values are initiallised.
	 * The the output flow rate of the reactor is calculated after three settings of the temperature (two over 100 and one below).
	 * Then the output flows are compared to make sure that higher temperature makes higher output rate and that the temperature being below 100 means no output.
	 */
	@Test
	public void TU33_SF09(){
		Reactor reactor = new Reactor("Reactor");
		Valve valve = new Valve("Valve");
		
		double[] reactorOutputFlow = {0,0,0};
		
		reactor.connectToInput(valve);
		reactor.connectToOutput(valve);
		valve.connectToInput(reactor);
		valve.connectToInput(reactor);
		valve.setAmount(30);
		valve.calculate();
		
		reactor.setControlRodLevel(50);
		reactor.setAmount(50);
		reactor.setTemperature(150);
		reactor.setWaterLevel(50);
		reactor.calculate();
	
		reactorOutputFlow[0] = reactor.getAmount();
		
		reactor.setTemperature(120);
		reactor.setControlRodLevel(50);
		reactor.setWaterLevel(50);
		reactor.setAmount(50);
		reactor.calculate();
	
		reactorOutputFlow[1] = reactor.getAmount();
		
		reactor.setTemperature(50);
		reactor.setControlRodLevel(50);
		reactor.setWaterLevel(50);
		reactor.setAmount(50);
		reactor.calculate();
	
		reactorOutputFlow[2] = reactor.getAmount();
		
		assertTrue("" + reactorOutputFlow[0] + " " + reactorOutputFlow[1] + " " +reactorOutputFlow[2], reactorOutputFlow[0] < reactorOutputFlow[1]); //Comparison inverted as less amount (of water) = more steam
		assertTrue("" + reactorOutputFlow[2],  reactorOutputFlow[2] == 50);  // Amount should stay at 50 as no steam is produced.
		
		
	}
	/**
	 * The higher the control rods are raised the higher the rate of increase of temperature in the reactor will be.
	 * A simple power plant is created with two components; a reactor and a valve.
	 * The values of these components are initialised, then the temperature of the reactor is read after a calculation is preformed after setting the control rods at various positions.
	 * These values are compared to assert than the higher the control rod level the higher the temperature.
	 */
	@Test
	public void TU34_SF10(){
		Reactor reactor = new Reactor("Reactor");
		Valve valve = new Valve("Valve");
		
		reactor.connectToInput(valve);
		reactor.connectToOutput(valve);
		valve.connectToInput(reactor);
		valve.connectToOutput(reactor);
		
		valve.setOuputFlowRate(50);
		
		reactor.setControlRodLevel(50);
		reactor.setWaterLevel(50);
		reactor.setAmount(50);
		reactor.calculate();
		
		Double temp = reactor.getTemperature();
		
		reactor.setControlRodLevel(70);
		
		reactor.calculate();
		
		Double temp1 = reactor.getTemperature();
		
		assertTrue("" + temp + " " + temp1, temp < temp1);
		
	}
	
	/**
	 * The pressure generated in the reactor tank is directly proportional to the amount of steam generated in the reactor, so the pressure should always increase with amount of steam in the reactor. 
	 * Creates a basic power plant with two components, a reactor and a valve. These two components are initialised then by changing the temperature the pressure is changed. After the temperature is changed, the reactor calculates and its pressure read.
	 * These pressures are compared to assert that lower temperature means lower pressure. This is due to higher temperatures producing more steam, which intern increases the pressure.
	 */
	@Test
	public void TU35_SF19(){
		Reactor reactor = new Reactor("Reactor");
		Valve valve = new Valve("Valve");
		

		reactor.connectToInput(valve);
		reactor.connectToOutput(valve);
		valve.connectToInput(reactor);
		valve.connectToInput(reactor);
		valve.setOuputFlowRate(50);
		
		reactor.setControlRodLevel(50);
		reactor.setAmount(50);
		reactor.setTemperature(120);
		reactor.setWaterLevel(50);
		reactor.calculate();
		
		Double pres1 = reactor.getPressure();
		
		reactor.setControlRodLevel(50);
		reactor.setAmount(50);
		reactor.setTemperature(150);
		reactor.setWaterLevel(50);
		reactor.calculate();
		
		Double pres2 = reactor.getPressure();
		
		assertTrue("" + pres1 + " " + pres2, pres1 < pres2);
		
	}
	/**
	 * The pressure in the condenser is directly proportional to the amount of steam in the condenser. The more steam the higher the pressure.
	 * Creates a simple power plant with a consenser in.
	 * Calculates the pressure in the condenser after setting the input flow of steam to two different values.
	 * These are compared to assert that less steam input means the condenser has less pressure.
	 */
	@Test
	public void TU36_SF20(){
		Condenser condenser = new Condenser("Condenser");
		Valve valve = new Valve("Valve");
		Valve valve1 = new Valve("Valve 2");
		
		valve.connectToOutput(condenser);
		valve.connectToInput(valve1);
		condenser.connectToInput(valve);
		condenser.connectToOutput(valve1);
		valve1.connectToInput(condenser);
		valve1.connectToOutput(valve);
		
		valve.setAmount(30);
		valve1.setAmount(30);
		valve.setTemperature(50);
		valve1.setTemperature(30);
		
		condenser.setAmount(50);
		condenser.setTemperature(50);
		
		valve.calculate();
		condenser.calculate();
		valve1.calculate();
		
		Double p1 = condenser.getPressure();
		
		valve.setAmount(60);
		valve1.setAmount(30);
		valve.setTemperature(50);
		valve1.setTemperature(30);
		
		condenser.setAmount(50);
		condenser.setTemperature(50);
		
		valve.calculate();
		condenser.calculate();
		valve1.calculate();
		
		Double p2 = condenser.getPressure();
		
		assertTrue("" + p1 + " " + p2, p1 < p2);
		
	}
	/**
	 * The condenser pressure being displayed for the user will always be the same as the current result of the condenser pressure calculation.
	 * Creates an instance of a GameEngine and a Condenser. The pressure of the condenser is retrieved and sent to the user interface for display. 
	 * The contents of the display are checked to contain the same pressure as that that was retrieved from the condenser.
	 */
	@Test
	public void TU37_SF21(){
		GameEngine ge = new GameEngine();
		Condenser condenser = new Condenser("Condenser");
	
		condenser.setAmount(50);
		condenser.setTemperature(50);
		condenser.setOuputFlowRate(50);
		condenser.connectToInput(condenser);
		condenser.connectToOutput(condenser);
		
		condenser.calculate();
		
		Double conPressure = condenser.getPressure();
		ArrayList<InfoPacket> infos = new ArrayList<InfoPacket>();
		InfoPacket condenserInfo = condenser.getInfo();
		infos.add(condenserInfo);
		
		ge.updateInterfaceComponents(infos);
		
		String outputString = ge.window.getRightPannelContence();
		
		assertTrue(conPressure.toString() + " Output: " + outputString, outputString.contains("Pressure: " + conPressure.toString()));
		
	}
	/**
	 * The reactor pressure being displayed for the user will always be the same as the current result of the reactor pressure calculation.
	 * Creates an instance of a GameEngine and a Reactor. The pressure of the Reactor is retrieved and sent to the user interface for display. 
	 * The contents of the display are checked to contain the same pressure as that that was retrieved from the Reactor.
	 */
	@Test
	public void TU38_SF22(){
		GameEngine ge = new GameEngine();
		Reactor reactor= new Reactor("Reactor");
	
		reactor.setAmount(50);
		reactor.setTemperature(50);
		reactor.setControlRodLevel(50);
		reactor.setOuputFlowRate(50);
		reactor.connectToInput(reactor);
		reactor.connectToOutput(reactor);
		
		reactor.calculate();
		
		Double conPressure = reactor.getPressure();
		ArrayList<InfoPacket> infos = new ArrayList<InfoPacket>();
		InfoPacket condenserInfo = reactor.getInfo();
		infos.add(condenserInfo);
		
		ge.updateInterfaceComponents(infos);
		
		String outputString = ge.window.getRightPannelContence();
		
		assertTrue(conPressure.toString() + " Output: " + outputString, outputString.contains("Pressure: " + conPressure.toString()));
	}
	/**
	 * The temperature inside the condenser will be proportional to the pressure in the condenser and the amount of cold water is being pumped round the condenser. The more pressure the hotter the condenser will be, and the more cold water is being pumped round the more the temperature will reduce.
	 * Creates a simple power plant, with a condenser valve and coolant pump. Tests whether raising the temperature of the condenser increases the pressure and whether increasing the coolant pump RPM reduces the temperature of the power plant.
	 */
	@Test
	public void TU39_SF23(){
		Condenser condenser = new Condenser("Condenser");
		Valve valve = new Valve("Valve");
		Pump coolentPump = new Pump("Coolant Pump");
		
		condenser.connectToInput(coolentPump);
		condenser.connectToInput(valve);
		condenser.connectToOutput(valve);
		valve.connectToInput(condenser);
		valve.connectToOutput(condenser);
		coolentPump.connectToInput(coolentPump);
		coolentPump.connectToOutput(coolentPump);
		coolentPump.connectToOutput(condenser);
		
		condenser.setAmount(50);
		condenser.setTemperature(120);
		
		coolentPump.setAmount(50);
		coolentPump.setRPM(100);
		coolentPump.setOuputFlowRate(50);
		
		valve.setAmount(50);
		valve.setTemperature(120);
		
		valve.calculate();
		coolentPump.calculate();
		condenser.calculate();
		
		Double cp1 = condenser.getPressure(); // pressure 1
		
		condenser.setAmount(50);
		condenser.setTemperature(150);
		
		coolentPump.setAmount(50);
		coolentPump.setRPM(100);
		coolentPump.setOuputFlowRate(50);
		
		valve.setAmount(50);
		valve.setTemperature(120);
		
		valve.calculate();
		coolentPump.calculate();
		condenser.calculate();
		
		Double cp2 = condenser.getPressure(); // pressure 2 ( after temp change)
		
		assertTrue("" + cp1 + " " + cp2, cp1 < cp2);
		
		condenser.setAmount(50);
		condenser.setTemperature(150);
		
		coolentPump.setAmount(100);
		coolentPump.setRPM(200);
		coolentPump.setOuputFlowRate(100);
		
		valve.setAmount(50);
		valve.setTemperature(120);
		
		valve.calculate();
		coolentPump.calculate();
		condenser.calculate();
		
		Double cp3 = condenser.getPressure();
		
		assertTrue("" + cp2 + " " + cp3, cp2 > cp3); // increasing coolant pump rpm should decrease temperature.
	}
	/**
	 * The reactor temperature being displayed for the user will always be the same as the current result of the reactor temperature calculation.
	 * Creates an instance of a GameEngine and a Reactor. The temperature of the Reactor is retrieved and sent to the user interface for display. 
	 * The contents of the display are checked to contain the same temperature as that that was retrieved from the Reactor.
	 */
	@Test
	public void TU40_SF24(){
		GameEngine ge = new GameEngine();
		Reactor reactor= new Reactor("Reactor");
	
		reactor.setAmount(50);
		reactor.setTemperature(50);
		reactor.setControlRodLevel(50);
		reactor.setOuputFlowRate(50);
		reactor.connectToInput(reactor);
		reactor.connectToOutput(reactor);
		
		reactor.calculate();
		
		Double conTemp = reactor.getTemperature();
		ArrayList<InfoPacket> infos = new ArrayList<InfoPacket>();
		InfoPacket condenserInfo = reactor.getInfo();
		infos.add(condenserInfo);
		
		ge.updateInterfaceComponents(infos);
		
		String outputString = ge.window.getRightPannelContence();
		
		assertTrue(conTemp.toString() + " Output: " + outputString, outputString.contains("Temperature: " + conTemp.toString()));
	}
	/**
	 * The condenser temperature being displayed for the user will always be the same as the current result of the condenser temperature calculation.
	 * Creates an instance of a GameEngine and a Condenser. The temperature of the condenser is retrieved and sent to the user interface for display. 
	 * The contents of the display are checked to contain the same Temperature as that that was retrieved from the condenser.
	 */
	@Test
	public void TU41_SF25(){
		GameEngine ge = new GameEngine();
		Condenser condenser = new Condenser("Condenser");
	
		condenser.setAmount(50);
		condenser.setTemperature(50);
		condenser.setOuputFlowRate(50);
		condenser.connectToInput(condenser);
		condenser.connectToOutput(condenser);
		
		condenser.calculate();
		
		Double conTemp = condenser.getTemperature();
		ArrayList<InfoPacket> infos = new ArrayList<InfoPacket>();
		InfoPacket condenserInfo = condenser.getInfo();
		infos.add(condenserInfo);
		
		ge.updateInterfaceComponents(infos);
		
		String outputString = ge.window.getRightPannelContence();
		
		assertTrue(conTemp.toString() + " Output: " + outputString, outputString.contains("Temperature: " + conTemp.toString()));
		
	}
	
	@Test
	public void TU42_SF26(){
		Reactor r = new Reactor("Reactor");
		Pump p = new Pump("Pump");
		Valve v = new Valve("Valve");
		
		r.connectToInput(p);
		r.connectToOutput(v);
		p.connectToInput(v);
		p.connectToOutput(r);
		v.connectToInput(r);
		v.connectToOutput(p);
		
		r.setAmount(50);
		r.setControlRodLevel(100);
		r.setTemperature(50);
		r.setOuputFlowRate(50);
		p.setAmount(50);
		p.setRPM(100);
		p.setOuputFlowRate(50);
		v.setAmount(50);
		v.setOuputFlowRate(50);
		
		p.calculate();
		v.calculate();
		r.calculate();
		Double a1 = r.getWaterLevel();
		
		r.calculate();
		Double a2 = r.getWaterLevel();
		
		
		
		assertTrue("" + a1 + " " + a2, a1 > a2); //Oposite way round to what you would expect as water level goes down when steam increases.
		
		r.setAmount(50);
		r.setControlRodLevel(0);
		r.setTemperature(50);
		r.setOuputFlowRate(50);
		p.setAmount(50);
		p.setRPM(100);
		p.setOuputFlowRate(50);
		v.setAmount(50);
		v.setOuputFlowRate(50);
		
		p.calculate();
		v.calculate();
		r.calculate();
		Double a3 = r.getWaterLevel();
		
		r.calculate();
		Double a4 = r.getWaterLevel();
		
		assertTrue("" + a1 + " " + a2, a1 > a2); //Oposite way round to what you would expect as water level goes down when steam increases.
		
	}
	/**
	 * All components apart from Valves and Generators will eventually fail. 
	 * Test proves that the Turbine component inevitably fails. 
	 */
	@Test
	public void TU52_SN11()
	{
		Turbine turbine1 = new Turbine("Turbine 1");
		InfoPacket info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Turbine 1"));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 30.0));
		while(!turbine1.getFailed())
			turbine1.calculate();
		assertTrue(turbine1.getFailed());
		
	}
}
