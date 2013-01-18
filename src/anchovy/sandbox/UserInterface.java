package anchovy.sandbox;

import anchovy.util.Packet;
import anchovy.util.Tag;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class sets up the format for UI classes.<br>
 * The connection UserInterface <-> GameEngine is handled by:
 * <li> commandQueue - whenever a command is give to the UI, it's pushed here
 * <li> updateView - this is called by the GameEngine and has the new coordinates needed to draw the UI
 * <br>
 * Only GameEngine <b>owns</b> the UI, and not vice versa ()
 * <br>
 * The UI needs as INPUT: temp and pressure for reactor tank, temp and pressure for condenser, water level for tank, water level for condenser
 * <br>
 * The UI produces as OUTPUT the following commands: LOWER/RAISE <x>, OPEN/CLOSE <valveNo>, TURN ON/OFF <pumpNo> 
 * @author andrei
 */
public abstract class UserInterface {
	// Data to display (visible in subclasses)
	protected Double reactorTemp, reactorPres, condenserTemp, condenserPres;
	protected Integer reactorLevel, condenserLevel;
	protected String userMessage;
	
	// Public stuff (communication to GameEngine)
	/**
	 * commandQueue is a buffer between the UI and GameEngine for the commands
	 * @PacketTags
	 * <li>lower (Integer): lower rod x%
	 * <li>raise (Integer): raise rod x%
	 * <li>open (Integer): open xth valve
	 * <li>close (Integer): close xth valve
	 * <li>turnON (Integer): turn on the xth pump
	 * <li>turnOFF (Integer): turn off the xth pump
	 */
	public ArrayList<Packet> commandQueue = null;
	
	/**
	 * <li>reactorTemp (Double): temperature in the reactor tank
	 * <li>reactorPres (Double): pressure in the reactor tank
	 * <li>condenserTemp (Double): temperature in the condenser
	 * <li>condenserPres (Double): pressure in the condenser
	 * <li>reactorLevel (Integer): water level in the reactor tank (percent)
	 * <li>condenserLevel (Integer): water level in the condenser
	 * @see Tag
	 */
	public abstract void updateView(Packet p);
	
	// Tools
	/**
	 * This updates the data in the UI (i.e. reactor and condenser stuff)
	 * @param p 
	 */
	protected void updateViewData(Packet p) {
		// Feel free to use this as a template
		for(Map.Entry<Tag, Object> entry : p.entrySet()) {
			switch(entry.getKey()) {
			case userMessage:
				userMessage = (String) entry.getValue();
				break;
			case reactorTemp: 
				reactorTemp = (Double) entry.getValue();
				break;

			case reactorPres: 
				reactorPres	= (Double) entry.getValue();
				break;

			case reactorLevel: 
				reactorLevel = (Integer) entry.getValue();
				break;

			case condenserTemp: 
				condenserTemp = (Double) entry.getValue();
				break;

			case condenserPres:
				condenserPres = (Double) entry.getValue();
				break;

			case condenserLevel:
				condenserLevel	= (Integer) entry.getValue();
				break;

			default: break;
			}
		}
	}
	
	//Constructors
		/**
		 * This default constructor allocates memory for commandQueue and initializes everything to zero
		 * <br>It performs no graphics operations, those happen in the subclasses
		 */
		public UserInterface(){
			commandQueue = new ArrayList<Packet>();
			reactorTemp = 0.0;
			reactorPres = 0.0;
			condenserTemp = 0.0;
			condenserPres = 0.0;
			reactorLevel = 0;
			condenserLevel = 0;
		}
}
