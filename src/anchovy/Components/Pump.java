/**
 * 
 */
package anchovy.Components;

import java.util.Iterator;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Pair.Label;

/**
 * This class is the representation of the pump within the power plant.
 * @author Harrison
 */
public class Pump extends Component {

	private double RPM;
	private double pumpFlowRatio = 0.5; // The ratio to which the ouput flow rate is proportional to the RPM of the pump

	/**
	 * @param name Unique name of the component
	 */
	public Pump(String name) {
		super(name);
	}


	public Pump(String name, InfoPacket info){
		super(name, info);
		Pair<?> currentpair = null;
		Iterator<Pair<?>> pi = info.namedValues.iterator();
		Label currentlabel = null;
		while(pi.hasNext()){
			currentpair = pi.next();
			currentlabel = currentpair.getLabel();
			switch (currentlabel){
			case RPMs:
				RPM = (Double) currentpair.second();
				break;
			default:
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see anchovy.Components.Component#getInfo()
	 */
	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		info.namedValues.add(new Pair<Double> (Label.RPMs, RPM));
		return info;
	}

	/* (non-Javadoc)
	 * @see anchovy.Components.Component#calucalte()
	 */
	@Override
	public void calculate() {
		super.setFailed(calculateFailed());
		if(!super.isFailed()){
			super.setOuputFlowRate(calculateOutputFlowRate());
		}else{
			super.setOuputFlowRate(0);
			RPM = 0;
		}
	}
	
	@Override
	protected boolean calculateFailed() {
		if(super.getFailureTime() == 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	/**
	 * The output flow rate of the pump is directly proportional to the RPM of the pump.
	 */
	protected double calculateOutputFlowRate() {
		//The output flow rate of the pump is directly proportional to the RPM of the pump.
		return RPM * getPumpFlowRatio(); 
	}

	/* (non-Javadoc)
	 * @see anchovy.Components.Component#takeInfo(anchovy.InfoProposal)
	 */
	@Override
	public void takeInfo(InfoPacket info) throws Exception {
		super.takeSuperInfo(info);
		Iterator<Pair<?>> i = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(i.hasNext()){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case RPMs:
				RPM = (Double) pair.second();
			default:
				break;
			}
		}
	}

	public double getPumpFlowRatio() {
		return pumpFlowRatio;
	}

	public void setPumpFlowRatio(double pumpFlowRatio) {
		this.pumpFlowRatio = pumpFlowRatio;
	}

	public double getRPM() {
		return RPM;
	}

	public void setRPM(double rPM) {
		RPM = rPM;
	}

}
