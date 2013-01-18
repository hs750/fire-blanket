package anchovy.sandbox.component;

import java.util.Map;

import anchovy.util.Packet;
import anchovy.util.Tag;

/**
 * This class is the representation of the condesner within the power plant - This component recives steam from another component,
 * then depending on what the state the power plant condensed it back to water to use elsewhere in the power plant.
 * 
 * @author Harrison
 */
public class Condenser extends Component {
	protected Double temperature;
	protected Double pressure;
	protected Double waterLevel;
	/**
	 * Set up the power plant, most work is done in Component.
	 * @param name The name of the component.
	 */
	public Condenser(String name) {
		super(name);
		this.type = "Condenser";
	}

	@Override
	public Packet toPacket() {
		Packet p = new Packet(super.getHeader());
		
		p.put(Tag.compTemperature, temperature);
		p.put(Tag.compPressure, pressure);
		p.put(Tag.compWaterLevel, waterLevel);
		
		return p;
	}

	@Override
	public void calculate() {
		double oldPressure = pressure;
		pressure = calculatePressure();
		temperature = calculateTemp(oldPressure);
		waterLevel = calculateWaterLevel();
		outputFlowRate = calculateOutputFlowRate();
	}
	/**
	 * Calculate the temperature of the condenser, 
	 * Temperature = old temp * ratio that pressure increased or decreased by - the coolant flow rate
	 * @param oldPressure pressure before the last calculation of pressure.
	 * @return The new temperature.
	 */
	protected double calculateTemp(double oldPressure){
		Double totalCoolantFlowRate = 0.0;
		
		for(Component c : receivesInputFrom) {
			if(c.name().toLowerCase().contains("coolant")) {
				totalCoolantFlowRate += c.outputFlowRate;
			}
		}
		
		Double ratio = pressure/oldPressure;
		
		return temperature * ratio - totalCoolantFlowRate;
	}
	
	/**
	 * Calculates the pressure within the condenser
	 * Pressure  = current pressure + input flow rate of steam - output flow rate of water.
	 * @return The new pressure
	 */
	protected double calculatePressure(){
		Double totalInputFlowRate = 0.0;
		
		for(Component c : receivesInputFrom) {
			if(c.name().toLowerCase().contains("coolant")) {
				totalInputFlowRate += c.outputFlowRate;
			}
		}
		
		if(temperature > 100) {
			return pressure + totalInputFlowRate - super.outputFlowRate;
		}
		
		return (pressure - pressure/temperature) + totalInputFlowRate - super.outputFlowRate;
	}
	
	
	/**
	 * Calculate the water level within the condenser
	 * water level = steam condensed + current water level - water flow rate out.
	 * @return The new water level.
	 */
	protected double calculateWaterLevel(){
		if(temperature > 100) {
			return waterLevel - super.outputFlowRate;
		}
		
		return waterLevel - super.outputFlowRate + (pressure/10);
	}

	/**
	 * Must distinguish between a pump that pumps steam in with a pump that pumps coolant round
	 * (possibly by name of pump having "Coolant" or alike in)
	 */
	@Override
	protected Double calculateOutputFlowRate() {
		Double totalOPFL = 0.0;
		
		for(Component c : outputsTo) {
			if(c.name().toLowerCase().contains("coolant") == false) {
				totalOPFL += c.outputFlowRate;
			}
		}
		
		return totalOPFL;
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
			case compWaterLevel:
				waterLevel = (Double) entry.getValue();
				break;
			default:
				break;
			}
		}
	}
}
