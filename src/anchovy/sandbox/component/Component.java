package anchovy.sandbox.component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import anchovy.util.Packet;
import anchovy.util.Tag;

/**
 * Fire Blanket game engine. The controller in MVC.
 * 
 * @author harrison
 * @edited ansimionescu
 */
public abstract class Component {
	// Data
	protected Integer meanTimeBetweenFailure;			// MTBF
	
	protected String name;
	protected String type;
	protected Double failureTime;
	protected Double outputFlowRate;
	protected ArrayList<Component> outputsTo;				// Component names
	protected ArrayList<Component> receivesInputFrom;		// Component names
	
	// Constructor
	/**
	 * Default constructor (minimal initialization)
	 * @param type
	 * @param name
	 */
	public Component(String name) {
		this.type = "N/A";
		this.name = name;
		this.outputsTo = new ArrayList<Component>();
		this.receivesInputFrom = new ArrayList<Component>();
		
		// The failure time is normally distributed around the MTBF
		this.failureTime = (new Random()).nextGaussian() * 10 + meanTimeBetweenFailure;
	}
	
	// Abstract methods
	public abstract Packet toPacket();
	public abstract void calculate();
	public abstract void setup(Packet p);
	protected abstract Double calculateOutputFlowRate();
	
	// Modifiers
	/**
	 * Repairs the component after it has failed
	 */
	public void repair() {
		failureTime = (new Random()).nextGaussian() * 10 + meanTimeBetweenFailure;
		
		// Code to repair the component TODO
	}
	
	// Util
	/**
	 * Creates a Packet with the essential information about this component ("header" because every Component subclass will contain it)
	 * @return Packet with name, type, failureTime, and outputFlowRate
	 */
	protected Packet getHeader() { 	// Formerly, getSuperInfo()
		Packet out = new Packet();
		
		out.put(Tag.compName, this.name);
		out.put(Tag.compType, this.type);
		out.put(Tag.failTime, this.failureTime);
		out.put(Tag.flowRate, this.outputFlowRate);
		
		for(Component c : this.outputsTo) {
			out.put(Tag.compOutput, c.name());
		}
		
		for(Component c : this.receivesInputFrom) {
			out.put(Tag.compInput, c.name());
		}
		
		return out;
	}
	
	protected void setupHeader(Packet p) {
		// Deleting the previous data
		this.resetConnections();
		
		for(Map.Entry<Tag, Object> i : p.entrySet()) {
			switch(i.getKey()) {
			case compOutput:
				this.addOutput((Component) i.getValue());
				break;
			case compInput:
				this.addInput((Component) i.getValue());
				break;
			case compName:
				this.name = (String) i.getValue();
				break;
			case compType:
				this.type = (String) i.getValue();
				break;
			case failTime:
				this.failureTime = (Double) i.getValue();
				break;
			case flowRate:
				this.outputFlowRate = (Double) i.getValue();
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Clears the list of components that this component is connected to
	 */
	protected void resetConnections() {
		this.outputsTo.clear();
		this.receivesInputFrom.clear();
	}
	
	// Getters, setters, modifiers
	public String type() {
		return this.type;
	}
	
	public String name() {
		return this.name;
	}
	
	public void addInput(Component c) {
		this.receivesInputFrom.add(c);
	}
	
	public void addOutput(Component c) {
		this.outputsTo.add(c);
	}
	
	/**
	 * Testing stuff
	 */
	public static void main(String[] args) {
		Packet p = new Packet();
		String x = "abc";
		
		p.put(Tag.test1, "b");
		p.put(Tag.test2, 2.23);
		p.put(Tag.test3, 1);
		p.put(Tag.test4, x);
		
		Packet p1 = new Packet(p);
		p1.put(Tag.name, p1.getClass());
		
		System.out.println(p1.toString());;
	}
}
