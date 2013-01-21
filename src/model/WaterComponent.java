package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import util.InfoPacket;
import util.MaxInputComparator;
import util.Pair;
import util.Pair.Label;


public abstract class WaterComponent extends Component {
	private Double temperature = 50.0;
	private Double amount = 500.0;
	private Double volume = 9000.0;
	private ArrayList<WaterComponent> outputsWaterTo = new ArrayList<WaterComponent>();

	public WaterComponent(String name) {
		super(name);	
	}

	public WaterComponent(String name, InfoPacket info) {
		super(name, info);
		Pair<?> currentpair = null;
		Iterator<Pair<?>> pi = info.namedValues.iterator();
		Label currentlabel = null;
		while(pi.hasNext()){
			currentpair = pi.next();
			currentlabel = currentpair.getLabel();
			switch (currentlabel){
			case temp:
				temperature = (Double) currentpair.second();
				break;
			case Vlme:
				volume = (Double) currentpair.second();
				break;
			case Amnt:
				amount = (Double) currentpair.second();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public InfoPacket getInfo() {
		InfoPacket wcompinfo = super.getInfo();
		wcompinfo.namedValues.add(new Pair<Double>(Label.Vlme, volume));
		wcompinfo.namedValues.add(new Pair<Double>(Label.Amnt, amount));
		wcompinfo.namedValues.add(new Pair<Double>(Label.temp, temperature));
		return wcompinfo;
	}

	@Override
	public void calculate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean calculateFailed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected double calculateOutputFlowRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void takeInfo(InfoPacket info) throws Exception {
		super.takeInfo(info);
		resetConections();
		Iterator<Pair<?>> i = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(i.hasNext()){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case Amnt:
				setAmount((Double) pair.second());
				break;
			case Vlme:
				setVolume((Double) pair.second());
				break;
			case temp:
				setTemperature((Double) pair.second());
				break;	
			default:
				break;
			}
		}

	}

	/**
	 * @param amnt Change amount of steam/water in component to this.
	 */
	public void setAmount(double amnt){
		amount = amnt;
	}
	

	public void connectToOutput(Component component){
		super.connectToOutput(component);
		if (component instanceof WaterComponent){
			outputsWaterTo.add((WaterComponent)component);
		}	
	}

	/**
	 * @return The amount of steam/water of the component
	 */
	public double getAmount(){ return amount;}

	/**
	 * @param vlme Change volume of component to this.
	 */
	public void setVolume(double vlme){
		volume = vlme;
	}
	/**
	 * @return The volume of the component
	 */
	public double getVolume(){ return volume;}

	/**
	 * @param temp Change temperature of component to this.
	 */
	public void setTemperature(double temp){
		temperature = temp;
	}
	/**
	 * @return The temperature of the component
	 */
	public double getTemperature(){ return temperature;}

	/**
	 * @return The pressure of the component, no actual variable as pressure is just a function of temp Amnt and Vlme
	 */
	public double getPressure() {
		return (getAmount()*getTemperature())/getVolume();
	}
	/**
	 * Recieves an amount/temp waterpacket
	 * @param info: InfoPacket containing a Temp and an Amnt
	 */
	public void recieveWater(InfoPacket info) {
		double packetTemp = 0.0;
		double packetAmount = 0.0;
		Pair<?> currentpair = null;
		Iterator<Pair<?>> pi = info.namedValues.iterator();
		Label currentlabel = null;
		while(pi.hasNext()){
			currentpair = pi.next();
			currentlabel = currentpair.getLabel();
			switch (currentlabel){
			case temp:
				packetTemp = (Double) currentpair.second();
				break;
			case Amnt:
				packetAmount = (Double) currentpair.second();
				break;
			default:
				break;
			}
		}	
		double totalPacketHeat = packetTemp * packetAmount;
		double totalInternalHeat = getTemperature() * getAmount();
		double totalWaterAmount = getAmount() + packetAmount;
		if (totalWaterAmount != 0){
			setTemperature((totalPacketHeat+ totalInternalHeat)/totalWaterAmount);			
		}
		setAmount(totalWaterAmount);
	}	

	public void sortOutputs(){
		Collections.sort( (List<WaterComponent>) outputsWaterTo, new MaxInputComparator() );
	}

	public void transmitOutputWater(){
		InfoPacket info = outputWater();
		Double watertemperature = 0.0;
		Double totalOutput = 0.0;
		Pair<?> currentPair;
		Label currentLabel;
		Iterator<Pair<?>> pairIt = info.namedValues.iterator();
		while (pairIt.hasNext()) {
			currentPair = pairIt.next();
			currentLabel = currentPair.getLabel();
			switch (currentLabel) {
			case Amnt:
				totalOutput = (Double) currentPair.second();
				break;
			case temp:
				watertemperature = (Double) currentPair.second();
				break;
			default:
				break;
			}
		}
		int count = outputsWaterTo.size();
		double outputPerComponent;
		WaterComponent comp;
		sortOutputs();
		Iterator<WaterComponent> cIt = outputsWaterTo.iterator();
		while(cIt.hasNext()){
			comp = cIt.next();
			outputPerComponent = (totalOutput / count);
			if (outputPerComponent > comp.maxInput()){
				totalOutput = totalOutput - comp.maxInput();// this is the bit where water is transferred
				InfoPacket waterpack = new InfoPacket();
				waterpack.namedValues.add(new Pair<Double>(Pair.Label.Amnt, comp.maxInput()));
				waterpack.namedValues.add(new Pair<Double>(Pair.Label.temp, watertemperature));
				comp.recieveWater(waterpack);
			}else{
				totalOutput = totalOutput - outputPerComponent;// this is the bit where water is transferred
				InfoPacket waterpack = new InfoPacket();
				waterpack.namedValues.add(new Pair<Double>(Pair.Label.Amnt, outputPerComponent));
				waterpack.namedValues.add(new Pair<Double>(Pair.Label.temp, watertemperature));
				comp.recieveWater(waterpack);
			}
			count = count - 1;
		}
		InfoPacket waterpack = new InfoPacket();
		waterpack.namedValues.add(new Pair<Double>(Pair.Label.Amnt, totalOutput));
		waterpack.namedValues.add(new Pair<Double>(Pair.Label.temp, watertemperature));
		this.recieveWater(waterpack);
	}


	public abstract double maxInput();

	public abstract InfoPacket outputWater();
}
