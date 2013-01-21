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
	private double RPM = 0.0;
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
	 * Calculate the RPM of the turbine
	 * RPM is based on the flow rate of steam in to the turbine / the ratio which energy is lost in pushing the turbine. 
	 * @return The RPM of the turbine from the current cycle
	 */
	protected double calculateRPM(){
		//RPM is proportional to the input flow rate of steam into the turbine.
		return getTotalInputFlowRate() / RPMRatio;
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
	 * Output flow rate of turbine is equal to the input flow rate.
	 *  ---DEPRECATED---
	 */
	@Override
	protected double calculateOutputFlowRate() {
		//OutputFlowRate = input flow rate
		return getTotalInputFlowRate();
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
			case RPMs:
				RPM = (Double) pair.second();
			default:
				break;
			}
		}
	}

	/**
	 * @return The revolutions per minute of the turbine
	 */
	public double getRPM() {
		return RPM;
	}
	/**
	 * @param RPM The revolutions per minute that the turbine will spin at.
	 */
	public void setRPM(double RPM) {
		this.RPM = RPM;
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
