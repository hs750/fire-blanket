package model;

import java.util.ArrayList;
import java.util.Iterator;

import util.InfoPacket;
import util.Pair;
import util.Pair.Label;


/**
 * This is the representation of the turbine within the power plant.
 * @author Harrison
 */
public class Turbine extends WaterComponent {
	/**
	 * Ratio governing how much of the steam flow transfers to RPM of the turbine.
	 */
	private double RPMRatio = 0.5; 
	/**
	 * @see model.Component#Component(String)
	 */
	public Turbine(String name) {
		super(name);
	}
	/** 
	 * @see model.Component#Component(String, InfoPacket)
	 */
	public Turbine(String name, InfoPacket info) {
		super(name, info);
		Pair<?> currentpair = null;
		Iterator<Pair<?>> pi = info.namedValues.iterator();
		Label currentlabel = null;
		while(pi.hasNext()){
			currentpair = pi.next();
			currentlabel = currentpair.getLabel();
			switch (currentlabel){
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
		InfoPacket info = super.getInfo();
		info.namedValues.add(new Pair<Double>(Label.RPMs, getRPM()));
		return info;
	}
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void calculate() {

		transmitOutputWater();
//		super.setFailed(calculateFailed());
//		if(!super.isFailed()){
//			RPM = calculateRPM();
//			super.setOuputFlowRate(calculateOutputFlowRate());
//		}else{
//			RPM = 0;
//			super.setOuputFlowRate(0);
//		}

	}
	/**
	 * {@inheritDoc}
	 * Turbines only fail when they reach the randomly generated fail time.
	 */
	@Override
	protected boolean calculateFailed(){
		if(super.getFailureTime() == 0){
			return true;
		}
		return false;
	}

	/**
	 * Get the total flow rate from components that is being input into this component.
	 * @return Total input flow rate.
	 *  ---DEPRECATED---
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

	/** 
	 * {@inheritDoc}
	 */
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
			default:
				break;
			}
		}
	}

	/**
	 * @return The revolutions per minute of the turbine
	 */
	public double getRPM() {
		return getOutputFlowRate() * getRPMRatio();
	}

	/**
	 * @return The ratio that steam flow round the turbine is converted to RPM
	 */
	public double getRPMRatio() {
		return RPMRatio;
	}

	/**
	 * @param RPMRatio The ratio at which the steam flow is converted to RPM
	 */
	public void setRPMRatio(double RPMRatio) {
		this.RPMRatio = RPMRatio;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InfoPacket outputWater() {
		InfoPacket waterpack = new InfoPacket();
		double packAmount = getPressure()/10;
		waterpack.namedValues.add(new Pair<Double>(Pair.Label.Amnt, packAmount));
		setAmount(getAmount() - packAmount);
		waterpack.namedValues.add(new Pair<Double>(Pair.Label.temp, getTemperature()));
		return waterpack;
	}
	
	@Override
	public double maxInput() {
		return getVolume() - getAmount();
	}


}
