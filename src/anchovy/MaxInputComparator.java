package anchovy;

import java.util.Comparator;

import anchovy.Components.*;

public class MaxInputComparator implements Comparator<WaterComponent> {
   
	@Override
	public int compare(WaterComponent o1, WaterComponent o2) {
		if (o1.maxInput() > o2.maxInput()){
			return 1;
		}
		else{
			return -1;
		}
	}
}
