/**
 * 
 */
package anchovy.sandbox.component;

import java.util.Map;

import anchovy.util.Packet;
import anchovy.util.Tag;

/**
 * Representation of Valve withing the power plant.
 * @author harrison
 */
public class Valve extends Component {
	protected Boolean position;

	public Valve(String name) {
		super(name);
		type = "Valve";
	}

	@Override
	public Packet toPacket() {
		Packet p = new Packet(super.getHeader());
		
		p.put(Tag.compPosition, position);
		
		return p;
	}

	@Override
	public void calculate() {
		super.outputFlowRate = calculateOutputFlowRate();
	}

	@Override
	/**
	 * Ouputut fow rate of valve is directly based of of the unput flow rate.
	 * If valve is open, output flow  = input flow
	 * otherwise the output flow = 0.
	 */
	protected Double calculateOutputFlowRate() {
		if(position == false)
			return 0.0;
		
		Double inFlowRate = 0.0;
		
		for(Component c : receivesInputFrom) {
			inFlowRate += c.outputFlowRate;
		}
		
		return inFlowRate;
	}

	@Override
	public void setup(Packet p) {
		super.setupHeader(p);
		
		for(Map.Entry<Tag, Object> entry : p.entrySet()) {
			switch(entry.getKey()) {
			case compPosition:
				position = (Boolean) entry.getValue();
				break;
			
			default:
				break;
			}
		}
	}

	/// TODO-later see what getters/setters are unnecessary and remove them
	public Boolean getPosition() {
		return position;
	}

	public void setPosition(Boolean position) {
		this.position = position;
	}
	
	
	/*
	 * sandbox
	 */
	public static void main(String[] args) {/*
		InfoPacket in = new InfoPacket();
		in.namedValues.add(new Pair<String>(Label.cNme, "Valve 1"));
		in.namedValues.add(new Pair<Double>(Label.falT, 1.23));
		in.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		in.namedValues.add(new Pair<Boolean>(Label.psit, true));
		Valve v = new Valve("Valve 1");
		try {
			v.takeInfo(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Valve v2 = new Valve("V2");
		v2.setOuputFlowRate(1234.567);
		v2.connectToInput(v);
		v.connectToOutput(v2);
		System.out.println("Flow from v2: " + v2.getOutputFlowRate());
		System.out.println("Flow out from v: " + v.getOutputFlowRate());
		v.setPosition(false);
		System.out.println("Valve is now closed");
		v.calculate();
		System.out.println("Flow out from v: " + v.getOutputFlowRate());
		*/ return;
	}

}
