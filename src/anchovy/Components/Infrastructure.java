package anchovy.Components;

import java.util.ArrayList;
import java.util.Iterator;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Pair.Label;

public class Infrastructure extends Component {
	//need to give electrisityneeded a value somewhere
	double electrisityneeded;
	boolean failed = false;
	public Infrastructure(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public Infrastructure(String name, InfoPacket info) {
		super(name, info);
	}



	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		return info;
	}

	@Override
	public void calculate() {
		calculateOutputFlowRate();
	}

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

	@Override
	public void takeInfo(InfoPacket info) throws Exception {
		super.takeSuperInfo(info);

	}

	@Override
	protected boolean calculateFailed() {
		if (electrisityneeded > this.getOutputFlowRate()){
			return true;
		}else{
			return false;
		}

	}

}
