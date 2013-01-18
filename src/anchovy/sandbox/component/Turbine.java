package anchovy.sandbox.component;

import java.util.Map;

import anchovy.util.Packet;
import anchovy.util.Tag;

/**
 * This is the representation of the tuebine within the poewr plant.
 * @author harrison
 */
public class Turbine extends Component {
	private Double RPM;
	private Double RPMRatio = 0.5; //Ratio governing how much of the steam flow transfers to rpm
	
	public Turbine(String name) {
		super(name);
		type = "Turbine";
	}

	@Override
	public Packet toPacket() {
		Packet p = new Packet(super.getHeader());
		
		p.put(Tag.compRPM, RPM);
		
		return p;
	}

	@Override
	public void calculate() {
		RPM = calculateRPM();
		super.outputFlowRate = calculateOutputFlowRate();
	}
	
	/**
	 * Calculate the RPM of the turbine
	 * RPM is based ont he fow rate of steam in to the turbine / the ratio which energy is lost in pushing the turbine. 
	 * RPM is proportional to the input flow rate of steam into the turbine.
	 * 
	 * @return
	 */
	protected Double calculateRPM(){
		return getTotalInputFlowRate() / RPMRatio;
	}
	
	/**
	 * Get the total flow rate from components that is being input into this component.
	 * @return Total input flow rate.
	 */
	protected Double getTotalInputFlowRate() {
		Double totalIPFL = 0.0;
		
		for(Component c : receivesInputFrom) {
			totalIPFL += c.outputFlowRate;
		}
		
		return totalIPFL;
	}
	
	@Override
	protected Double calculateOutputFlowRate() {
		//OutputFlowRate = input flow rate
		return getTotalInputFlowRate();
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
	
	/// TODO-later see what getters/setters are unnecessary and remove them
	public Double RPM() {
		return RPM;
	}

	public void setRPM(double rPM) {
		RPM = rPM;
	}

	public Double getRPMRatio() {
		return RPMRatio;
	}

	public void setRPMRatio(double rPMRatio) {
		RPMRatio = rPMRatio;
	}
}
