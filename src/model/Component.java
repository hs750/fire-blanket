package model;

import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

import util.InfoPacket;
import util.Pair;
import util.Pair.Label;


/**
 * A general component, all components within the power plant are children of this class.
 * Contains all common attributes and methods of components.
 * 
 * @author Harrison
 */
public abstract class Component {
	private String name;
	private int meanTimeBetweenFailure; //MTBF
	private Double failureTime;
	private Double outputFlowRate = 0.0;	//---DEPRECATED---
	private ArrayList<Component> outputsTo = new ArrayList<Component>();
	private ArrayList<Component> receivesInputFrom = new ArrayList<Component>();
	private boolean failed = false; //TODO make it so that this will work with infoPackets

	/**
	 * Setup the component ready for use, set its name and initialise the lists of components it is connected to.
	 * @param name the name of the individual component, should be unique.
	 */
	public Component(String name){
		this.name = name;
		if(failureTime == null){
			calcRandomFailTime();
		}
		outputsTo = new ArrayList<Component>();
		receivesInputFrom = new ArrayList<Component>();
	}
	/**
	 * Setup the component ready for use, including taking info to set attributes of component.
	 * Also set its name and initialise the lists of components it is connected to.
	 * @param name The name of the component 
	 * @param info The infoPacket to use to initialise the component
	 */
	public Component(String name, InfoPacket info){
		outputsTo = new ArrayList<Component>();
		receivesInputFrom = new ArrayList<Component>();

		this.name = name;
		Pair<?> currentpair = null;
		Iterator<Pair<?>> pi = info.namedValues.iterator();
		Label currentlabel = null;
		while(pi.hasNext()){
			currentpair = pi.next();
			currentlabel = currentpair.getLabel();
			switch (currentlabel){
			case falT:
				failureTime = (Double) currentpair.second();
				break;
			case OPFL:
				outputFlowRate = (Double) currentpair.second();
				break;
			default:
				break;
			}
		}
		if(failureTime == null){ // if there was no falT Info
			calcRandomFailTime();
		}
	}

	/**
	 * Calculates the failure time of the component normally distributed around the MTBF
	 */
	protected void calcRandomFailTime(){
		Random rand = new Random();
		failureTime = rand.nextGaussian()* 10 + meanTimeBetweenFailure;
	}

	/**
	 * Repairs the component. Will work at any time, even if the component has not failed.
	 */
	public void repair(){
		setFailed(false);
		calcRandomFailTime();
	}


	/**
	 * Assigns the values stored in the given info packet to the relevant attributes.
	 * Renaming the component is possible via this method so care may be needed. 
	 * This method does not deal with connecting components together, this must be done at game engine as components can't see other components until they are connected.
	 * @param info An info packet the the component. 
	 * ---DEPRECATED---
	 */
	protected void takeSuperInfo(InfoPacket info){
		resetConections();
		Iterator<Pair<?>> i = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(i.hasNext()){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case cNme:
				setName((String) pair.second());
				break;
			case falT:
				setFailureTime((Double) pair.second());
				break;
			case OPFL:
				setOuputFlowRate((Double) pair.second());	
			default:
				break;
			}
		}
	}

	/**
	 * Clears the lists of components that this component is connected to. 
	 */
	protected void resetConections(){
		outputsTo.clear();
		receivesInputFrom.clear();
	}

	/**
	 * @param name Change name of component to this.
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * @return The name of the component
	 */
	public String getName(){ return name;}

	/**
	 * Connects the given component to the list of components that are output to.
	 * @param  component Component to add to outputs.
	 */
	public void connectToOutput(Component component){
		outputsTo.add(component);
	}

	/**
	 * Connects the given component to the list of components that this component recives input from.
	 * @param component Component to add to inputs
	 */
	public void connectToInput(Component component){
		receivesInputFrom.add(component);
	}

	/**
	 * @param outputFlowRate The rate at which water/steam/electricity/other output is being output from this component.
	 * ---DEPRECATED---
	 */
	public void setOuputFlowRate(double outputFlowRate){
		this.outputFlowRate = outputFlowRate;
	}

	/**
	 * @return The output flow rate of this component.
	 * ---DEPRECATED
	 */
	public double getOutputFlowRate(){
		return outputFlowRate;
	}

	/**
	 * Create an info packet containing data about all attributes for the component
	 * @return info An info packet containing all attributes for the component
	 */
	public InfoPacket getInfo(){
		InfoPacket compinfo = new InfoPacket();
		compinfo.namedValues.add(new Pair<String>(Label.cNme, getName()));
		compinfo.namedValues.add(new Pair<Double>(Label.falT, getFailureTime()));

		Iterator<Component> i = outputsTo.iterator();
		Component c = null;
		while (i.hasNext()){
			c = i.next();
			compinfo.namedValues.add(new Pair<String>(Label.oPto, c.getName()));
		}
		i = receivesInputFrom.iterator();
		while (i.hasNext()){
			c = i.next();
			compinfo.namedValues.add(new Pair<String>(Label.rcIF, c.getName()));
		}
		return compinfo;
	}

	/**
	 * By having a single calculate method, any component can be told to calculate
	 * Without the rest of the program explicitly knowing what type of component it is.
	 * This method should therefore call separate (more specific calculates) within the actual component.
	 * such as calculateTemperature()
	 */
	public abstract void calculate();
	/**
	 * Calculate whether the component has failed or not.
	 * @return The failure state of the component.
	 */
	protected abstract boolean calculateFailed();

	/**
	 * The only specific calculation that all components must do as every component has an output flow.
	 * Abstract because every type of component will calculate this in a different way.
	 * @return The new output flow rate
	 * ---DEPRECATED---
	 */
	protected abstract double calculateOutputFlowRate();

	/**
	 * Sets all attributes of a component using the given info packet.
	 * Abstract as this deals with the component child specific attributes.
	 * should call super.takeSuperInfo()
	 * @param info InfoPacket defining values of attributes of the component.
	 */
	public void takeInfo(InfoPacket info) throws Exception{
		resetConections();
		Iterator<Pair<?>> i = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(i.hasNext()){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case cNme:
				setName((String) pair.second());
				break;
			case falT:
				setFailureTime((Double) pair.second());
				break;
			case OPFL:
				setOuputFlowRate((Double) pair.second());	
			default:
				break;
			}
		}
	}

	/**
	 * @return The mean time between failure of this component.
	 */
	public int getMeanTimeBetweenFailure() {
		return meanTimeBetweenFailure;
	}

	/**
	 * @param meanTimeBetweenFailure Change the components mean time between failure to this.
	 */
	public void setMeanTimeBetweenFailure(int meanTimeBetweenFailure) {
		this.meanTimeBetweenFailure = meanTimeBetweenFailure;
	}

	/**
	 * @return The time when this component will fail.
	 */
	public Double getFailureTime() {
		return failureTime;
	}

	/**
	 * @param failureTime Manually change the time that the component will fail
	 */
	public void setFailureTime(Double failureTime) {
		this.failureTime = failureTime;
	}

	/**
	 * @return The list containing all the components that this component outputs to.
	 */
	public ArrayList<Component> getOutputsTo() {
		return outputsTo;
	}

	/**
	 * @param outputsTo The list of components that this component will output to.
	 */
	public void setOutputsTo(ArrayList<Component> outputsTo) {
		this.outputsTo = outputsTo;
	}

	/**
	 * @return The list containing all the components that this component recives inupt from.
	 */
	public ArrayList<Component> getRecievesInputFrom() {
		return receivesInputFrom;
	}

	/**
	 * @param recievesInputFrom The list of components that this component will take input from.
	 */
	public void setRecievesInputFrom(ArrayList<Component> recievesInputFrom) {
		this.receivesInputFrom = recievesInputFrom;
	}



	/**
	 * @return Whether the component is currently failed or not. 
	 */
	public boolean isFailed() {
		return failed;
	}
	/**
	 * Set the state of the component to failed or not, used by child components to access and set the failed field when calculating whether the component has failed.
	 * @param failed Component in failed state?
	 */
	synchronized public void setFailed(boolean failed) {
		this.failed = failed;
	}

}