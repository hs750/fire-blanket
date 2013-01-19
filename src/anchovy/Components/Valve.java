/**
 * 
 */
package anchovy.Components;

import java.util.ArrayList;
import java.util.Iterator;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Pair.Label;

/**
 * Representation of Valve within the power plant.
 * @author Harrison
 */
public class Valve extends WaterComponent {
	/**
	 * Whether the valve is open(True) or closed(False).
	 */
	private Boolean position;

	/**
	 * @see anchovy.Components.Component#Component(String)
	 */
	public Valve(String name) {
		super(name);
		position = true;
	}

	/** 
	 * @see anchovy.Components.Component#Component(String, InfoPacket)
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
		info.namedValues.add(new Pair<Boolean>(Label.psit, position));
		return info;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void calculate() {
		super.setFailed(calculateFailed());
		super.setOuputFlowRate(calculateOutputFlowRate());


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

	public static void main(String[] args){
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

}
