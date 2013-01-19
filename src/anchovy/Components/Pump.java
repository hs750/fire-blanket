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
	/** 
	 * The ratio to which the output flow rate of the pump is proportional to its RPM
	 */
	private double pumpFlowRatio = 0.5; 

	/**
	 * @see anchovy.Components.Component#Component(String)
	 */
	public Pump(String name) {
		super(name);
	}

	/** 
	 * @see anchovy.Components.Component#Component(String, InfoPacket)
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		info.namedValues.add(new Pair<Double> (Label.RPMs, RPM));
		return info;
	}

	/** 
	 * {@inheritDoc}
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
	
	/** 
	 * {@inheritDoc}
	 * Pumps only fail when they reach there randomly calculated failure time.
	 */
	@Override
	protected boolean calculateFailed() {
		if(super.getFailureTime() == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * The output flow rate of the pump is directly proportional to the RPM of the pump.
	 */
	@Override
	protected double calculateOutputFlowRate() {
		//The output flow rate of the pump is directly proportional to the RPM of the pump.
		return RPM * getPumpFlowRatio(); 
	}

	/** 
	 * {@inheritDoc}
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

	/**
	 * @return The ratio at which the pumps RPM is converted to water flow
	 */
	public double getPumpFlowRatio() {
		return pumpFlowRatio;
	}

	/** 
	 * @param pumpFlowRatio The ratio at which the pump with convert its RPM to water flow rate.
	 */
	public void setPumpFlowRatio(double pumpFlowRatio) {
		this.pumpFlowRatio = pumpFlowRatio;
	}

	/**
	 * @return The revolutions per minute of the pump
	 */
	public double getRPM() {
		return RPM;
	}
	/**
	 * @param RPM The revolutions per minute that the pump with spin at.
	 */
	public void setRPM(double RPM) {
		this.RPM = RPM;
	}

}
