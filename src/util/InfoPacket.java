package util;

import java.util.ArrayList;

import util.Pair.Label;


/**
 * The class is used as the communication protocol throughout the game.
 * It consists of a list of pairs which contain the a label representing what the pair contains and a value which if the value associated with the label.
 * Components, loading the game and user commands create info packets, then components, saving the game and the output of the user interface receive info packets.
 * 
 * @author Andrei
 * @author Harrison
 *
 */
public class InfoPacket {
	/**
	 * ArrayList containing the data == (string, value) pairs
	 */
	public ArrayList<Pair<?>> namedValues = new ArrayList<Pair<?>>();
	
	/**
	 * {@inheritDoc}
	 * Determine whether two infoPackets are essentially the same. That is they have the same info pairs
	 * however not necessarily in the same order. 
	 */
	@Override
	public boolean equals(Object _infoPacket) {
		if(_infoPacket instanceof InfoPacket){
			InfoPacket infoPacket = (InfoPacket) _infoPacket;

			if(this.namedValues.size() != infoPacket.namedValues.size())
				return false;

			for(Pair<?> p : namedValues) {
				if(infoPacket.hasPair(p) == false)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Determines whether the infoPacket contains a particular pair. 
	 * @param p The pair that you are checking the existence of.
	 * @return Whether the pair p was in the infoPacket.
	 */
	public Boolean hasPair(Pair<?> p) {
		for(Pair<?> pair : namedValues) {
			if(p.equals(pair))
				return true;
		}		
		return false;
	}
	
	
	/**
	 * Sand box:
	 * <li>			feel free to use Pair constructs here
	 * <li>			run in Debug (F11)
	 * <li> 		also give Pair.Label, the enum, a try
	 */
	public static void main(String args[]) {
		// adding an integer to the Info object
		InfoPacket i = new InfoPacket();
		i.namedValues.add(new Pair<Integer>(Pair.Label.RPMs, 1));
		
		// adding a double value
		i.namedValues.add(new Pair<Double>(Pair.Label.coRL, 2.0));
		
		// adding a String 
		i.namedValues.add(new Pair<String>(Pair.Label.pres, "potato"));
		
		// adding an ArrayList
		i.namedValues.add(new Pair<ArrayList<Integer>>(Pair.Label.etc, new ArrayList<Integer>(3)));
		
		// defining an ArrayList with words and adding it to this Info object
		ArrayList<String> words = new ArrayList<String>();
		words.add("meow");
		words.add("cow");
		words.add("pixels");
		Pair<ArrayList<String>> x = new Pair<ArrayList<String>>(Pair.Label.etc, words);
		
		i.namedValues.add(x);
		
		// Printing the namedValues ArrayList
		System.out.println(i.namedValues);
		
		InfoPacket f = new InfoPacket();
		f.namedValues.add(new Pair<Integer>(Label.temp, 100));
		f.namedValues.add(new Pair<Integer>(Label.temp, 200));
		
		InfoPacket d = new InfoPacket();
		d.namedValues.add(new Pair<Integer>(Label.temp, 100));
		d.namedValues.add(new Pair<Integer>(Label.temp, 200));
		
		System.out.println(d.equals(f));
	}
}