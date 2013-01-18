/**
 * 
 */
package anchovy.sandbox.component;

import java.util.Map;

import anchovy.util.Packet;
import anchovy.util.Tag;

/**
 * This class is the representation of the pump within the power plant.
 * @author harrison
 * @edited ansimionescu
 */
public class Pump extends Component {
	protected Double RPM;
	protected Double pumpFlowRatio; // The ratio to which the ouput flow rate is proportional to the RPM of the pump
	
	public Pump(String name) {
		super(name);
		
		type = "Component";
		this.pumpFlowRatio = 0.5;
	}

	@Override
	public Packet toPacket() {
		Packet p = new Packet(super.getHeader());
		
		p.put(Tag.compRPM, RPM);
		
		return p;
	}

	/**
	 * @see anchovy.Components.Component#calculate()
	 */
	@Override
	public void calculate() {
		super.outputFlowRate = calculateOutputFlowRate();
	}

	@Override
	/**
	 * The output flow rate of the pump is directly proportional to the RPM of the pump.
	 */
	protected Double calculateOutputFlowRate() {
		return RPM * this.pumpFlowRatio;
	}

	@Override
	public void setup(Packet p) {
		super.setupHeader(p);
		
		for(Map.Entry<Tag, Object> entry : p.entrySet()) {
			switch(entry.getKey()) {
			case compRPM:
				RPM = (Double) entry.getValue();
				break;
			default:
				break;
			}
		}
	}
	
	public double pumpFlowRatio() {
		return pumpFlowRatio;
	}

	public void setPumpFlowRatio(double pumpFlowRatio) {
		this.pumpFlowRatio = pumpFlowRatio;
	}
	
	public double RPM() {
		return RPM;
	}

	public void setRPM(double rPM) {
		RPM = rPM;
	}

}
