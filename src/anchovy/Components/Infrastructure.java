package anchovy.Components;

import java.util.ArrayList;
import java.util.Iterator;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Pair.Label;

public class Infrastructure extends Component {
	/**
	 * The electricity that is needed to power this piece of infrastructure.
	 */
	double electrisityneeded;
	boolean failed = false;
	
	/**
	 * @see anchovy.Components.Component#Component(String)
	 */
	public Infrastructure(String name) {
		super(name);
	}
	/**
	 * @see anchovy.Components.Component#Component(String, InfoPacket)
	 */
	public Infrastructure(String name, InfoPacket info) {
		super(name, info);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		return info;
	}
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void calculate() {
		calculateOutputFlowRate();
	}
	
	/** 
	 * {@inheritDoc}
	 * The output flow rate of an infrastructure component is the total amount of electricity flowing into the component, minus the electricity that it itself needs to function.
	 */
	@Override
	protected double calculateOutputFlowRate() {
		double electrisityGenerated = 0;
		ArrayList<Component> inputComponents = super.getRecievesInputFrom();
		Iterator<Component> it = inputComponents.iterator();
		Component comp = null;
		while(it.hasNext()){
			comp = it.next();
			if((comp instanceof Generator) | (comp instanceof Infrastructure) ){
				electrisityGenerated += comp.getOutputFlowRate();
			}
			electrisityGenerated = electrisityGenerated - electrisityneeded;
			}
		return electrisityGenerated;
	}
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void takeInfo(InfoPacket info) throws Exception {
		super.takeSuperInfo(info);

	}
	/**
	 * {@inheritDoc}
	 * Infrastructure components fail when they are receiving less electricity than they need.
	 */
	@Override
	protected boolean calculateFailed() {
		if (electrisityneeded > this.getOutputFlowRate()){
			return true;
		}else{
			return false;
		}

	}

}
