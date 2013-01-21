/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;

import util.InfoPacket;
import util.Pair;
import util.Pair.Label;


/**
 * Representation of Valve within the power plant.
 * @author Harrison
 */
public class Valve extends WaterComponent {
	/**
	 * Whether the valve is open(True) or closed(False).
	 */
	private Boolean position = true;

	/**
	 * @see model.Component#Component(String)
	 */
	public Valve(String name) {
		super(name);
		position = true;
	}

	/** 
	 * @see model.Component#Component(String, InfoPacket)
	 */
	public Valve(String name, InfoPacket info) {
		super(name, info);
		Pair<?> currentpair = null;
		Iterator<Pair<?>> pi = info.namedValues.iterator();
		Label currentlabel = null;
		while(pi.hasNext()){
			currentpair = pi.next();
			currentlabel = currentpair.getLabel();
			switch (currentlabel){
			case psit:
				position = (Boolean) currentpair.second();
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
		info.namedValues.add(new Pair<Boolean>(Label.psit, getPosition()));
		return info;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void calculate() {

		transmitOutputWater();
//		super.setFailed(calculateFailed());
//		super.setOuputFlowRate(calculateOutputFlowRate());


	}

	/** 
	 * {@inheritDoc}
	 * Valves cannot fail so, calculating whether it has failed will always return false.
	 */
	@Override
	protected boolean calculateFailed() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * Output flow rate of valve is directly based of of the input flow rate.
	 * If valve is open, output flow  = input flow
	 * otherwise the output flow = 0.
	 *  ---DEPRECATED---
	 */
	@Override
	protected double calculateOutputFlowRate() {
		if(position){
			ArrayList<Component> recievesInputFrom = super.getRecievesInputFrom();
			Iterator<Component> it = recievesInputFrom. iterator();
			Double inFlowRate = 0.0;
			Component comp = null;
			while (it.hasNext()){
				comp = it.next();
				inFlowRate += comp.getOutputFlowRate();
			}
			return inFlowRate;
		}else{
			return 0;
		}

	}

	/**
	 * @return The boolean representation of the position of the valve, false = closed, true = open
	 */
	public Boolean getPosition() {
		return position;
	}
	/**
	 * @param position The position that the valve will be in, false = closed, true = open
	 */
	public void setPosition(Boolean position) {
		this.position = position;
	}


	/**
	 * @param position The position that the valve will be in, false = closed, true = open
	 */
	public double getMaximumInput() {
		if(position){
			return 9000;
		}else{
			return 0;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void takeInfo(InfoPacket info) throws Exception {
		super.takeInfo(info);
		Iterator<Pair<?>> i = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(i.hasNext()){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case psit:
				position = (Boolean) pair.second();
			default:
				break;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public InfoPacket outputWater() {
		InfoPacket waterpack = new InfoPacket();
		double packAmount = getAmount();
		waterpack.namedValues.add(new Pair<Double>(Pair.Label.Amnt, packAmount));
		setAmount(getAmount() - packAmount);
		waterpack.namedValues.add(new Pair<Double>(Pair.Label.temp, getTemperature()));
		return waterpack;
	}

	@Override
	public double maxInput() {
		if (position = true){
			return getVolume() - getAmount();
		}
		else{
			return 0;
		}
	}

}
