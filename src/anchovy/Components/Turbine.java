package anchovy.Components;

import java.util.ArrayList;
import java.util.Iterator;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Pair.Label;

/**
 * This is the representation of the tuebine within the poewr plant.
 * @author Harrison
 */
public class Turbine extends Component {
	private double RPM;
	private double RPMRatio = 0.5; //Ratio governing how much of the steam flow transfers to rpm
	/**
	 * 
	 * @param name Unique name of the turbine.
	 */
	public Turbine(String name) {
		super(name);
	}

	public Turbine(String name, InfoPacket info) {
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

	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		info.namedValues.add(new Pair<Double>(Label.RPMs, RPM));
		return info;
	}

	@Override
	public void calculate() {
		super.setFailed(calculateFailed());
		if(!super.isFailed()){
			RPM = calculateRPM();
			super.setOuputFlowRate(calculateOutputFlowRate());
		}else{
			RPM = 0;
			super.setOuputFlowRate(0);
		}
		
	}
	
	@Override
	protected boolean calculateFailed(){
		if(super.getFailureTime() == 0){
			return true;
		}
		return false;
	}
	/**
	 * Calculate the RPM of the turbine
	 * RPM is based ont he fow rate of steam in to the turbine / the ratio which energy is lost in pushing the turbine. 
	 * @return
	 */
	protected double calculateRPM(){
		//RPM is proportional to the input flow rate of steam into the turbine.
		return getTotalInputFlowRate() / RPMRatio;
	}

	/**
	 * Get the total flow rate from components that is being input into this component.
	 * @return Total input flow rate.
	 */
	private double getTotalInputFlowRate() {
		ArrayList<Component> inputs = super.getRecievesInputFrom();
		Iterator<Component> it = inputs.iterator();
		Component c = null;

		double totalIPFL = 0;
		while(it.hasNext()){
			c = it.next();
			totalIPFL =+ c.getOutputFlowRate();
		}
		return totalIPFL;
	}

	@Override
	protected double calculateOutputFlowRate() {
		//OutputFlowRate = input flow rate
		return getTotalInputFlowRate();
	}

	@Override
	public void takeInfo(InfoPacket info) throws Exception {
		super.takeSuperInfo(info);
		Iterator<Pair<?>> it = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(it.hasNext()){
			pair = it.next();
			label = pair.getLabel();
			switch(label){
			case RPMs:
				RPM = (Double) pair.second();
			default:
				break;
			}
		}
	}

	public double getRPM() {
		return RPM;
	}

	public void setRPM(double rPM) {
		RPM = rPM;
	}

	public double getRPMRatio() {
		return RPMRatio;
	}

	public void setRPMRatio(double rPMRatio) {
		RPMRatio = rPMRatio;
	}

}
