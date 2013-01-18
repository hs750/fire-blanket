package anchovy.sandbox.component;

import java.util.Map;

import anchovy.util.Packet;
import anchovy.util.Tag;
/**
 * This is the prepresentation of the Reactor withing the power plant.
 * @author harrison
 * @edited ansimionescu
 */
public class Reactor extends Component {
	protected Double temperature;
	protected Double pressure;
	protected Double controlRodLevel;
	protected Double waterLevel = 50.0;
	
	public Reactor(String name){
		super(name);
		type = "Reactor";
	}

	@Override
	public Packet toPacket() {
		Packet p = new Packet(super.getHeader());
		
		p.put(Tag.compTemperature, temperature);
		p.put(Tag.compPressure, pressure);
		p.put(Tag.compControlRodLevel, controlRodLevel);
		p.put(Tag.compWaterLevel, waterLevel);
		
		return p;
	}

	@Override
	public void calculate() {
		Double oldTemp = temperature;
		
		temperature = calculateTemperature();
		pressure = calcuatePressure(oldTemp);
		waterLevel = calculateWaterLevel();
		
		super.outputFlowRate = calculateOutputFlowRate();
	}


	/**
	 * Calculate the temperature of the reactor.
	 * The temperature of the reactor depends on the Control Rods.
	 * When the are lowered, the reactor gradually cools, when they are raised the Reactor get hotter and hotter.
	 * @return The new Temperature of the reactor.
	 */
	protected Double calculateTemperature(){
		if(temperature > 100) {
			return temperature + temperature * ((controlRodLevel-50)/2);
		}
		
		return temperature + temperature * ((controlRodLevel-5)/2);
	}
	
	/**
	 * Calculate the new pressure of the reactor.
	 * Pressure = Temperature * constant from Pressure Temperature law
	 * 
	 * @param oldTemp The old temperature of the reactor from last iteration.
	 * @return The new pressure of the reactor.
	 */
	protected Double calcuatePressure(double oldTemp){
		return pressure * (temperature/oldTemp);
	}
	
	/**
	 * Calculate the water level in the reactor.
	 * Water level = water level  + input fow rates - rate of steam production.
	 * @return The new water level in the reacotr
	 */
	protected Double calculateWaterLevel(){
		Double inputFlowRate = 0.0;
		
		for(Component c : this.receivesInputFrom) {
			inputFlowRate += c.outputFlowRate;
		}
		
		return inputFlowRate + waterLevel - super.outputFlowRate;
	}

	@Override
	protected Double calculateOutputFlowRate(){
		// OPFL is proportional to the pressure. 
		return pressure / 2;
	}
	
	@Override
	public void setup(Packet p) {
		super.setupHeader(p);
		
		for(Map.Entry<Tag, Object> entry : p.entrySet()) {
			switch(entry.getKey()) {
			case compTemperature:
				temperature = (Double) entry.getValue();
				break;
				
			case compPressure:
				pressure = (Double) entry.getValue();
				break;
				
			case compControlRodLevel:
				controlRodLevel = (Double) entry.getValue();
				break;
			
			case compWaterLevel:
				waterLevel = (Double) entry.getValue();
				break;
				
			default:
				break;
			}
		}
		
	}
	
	public double controlRodLevel() {
		return controlRodLevel;
	}
	
	public void setControlRodLevel(Double newControlRodLevel) {
		this.controlRodLevel = newControlRodLevel;
		
		if(controlRodLevel > 100 || controlRodLevel < 0)
			controlRodLevel = (controlRodLevel < 0)? 0.0 : 100.0;
	}

	public double getWaterLevel() {
		return waterLevel;
	}

	public void setWaterLevel(double waterLevel) {
		this.waterLevel = waterLevel;
	}

}
