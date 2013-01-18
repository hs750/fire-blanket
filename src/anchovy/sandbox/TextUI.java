package anchovy.sandbox;

import java.util.StringTokenizer;

import anchovy.util.Packet;
import anchovy.util.Tag;

public class TextUI extends UserInterface {
	// Interpreting commands
	/**
	 * Parses a command and pushes it onto the commandQueue
	 * @Commands
	 * <li> LOWER 	Integer Integer lowers rods x y%
	 * <li> RAISE 	Integer Integer	raises rods x y%
	 * <li> OPEN 	Integer			opens valve x
	 * <li> CLOSE 	Integer			closes valve x
	 * <li> ON 		Integer			turns valve x on
	 * <li> OFF 	Integer			turns valve x off
	 * <li> SAVE 	String			saves game to curDir/x
	 * <li> LOAD 	String			loads game from curDir/x
	 * <li> LOADA 	String			loads game from x (absolute path)
	 */
	protected void parseCommand(String comm) {
		Packet p = new Packet();
		StringTokenizer tok = new StringTokenizer(comm, " \"\t");
		String first = tok.nextToken().toUpperCase();
		
		if(tok.hasMoreTokens() == false) return;
		String second = tok.nextToken();
		
		// LOWER and RAISE have an extra argument
		switch(first) {
			case "LOWER": {
				p.put(Tag.comLower, new Integer(Integer.parseInt(second)));
				if(tok.hasMoreTokens() == false) return;
				p.put(Tag.intVal, new Integer(Integer.parseInt(tok.nextToken())));
				break;}
			
			case "RAISE":
				p.put(Tag.comRaise, new Integer(Integer.parseInt(second)));
				if(tok.hasMoreTokens() == false) return;
				p.put(Tag.intVal, new Integer(Integer.parseInt(tok.nextToken())));
				break;
			
			case "OPEN":
				p.put(Tag.comOpen, new Integer(Integer.parseInt(second)));
				break;
			
			case "CLOSE":
				p.put(Tag.comClose, new Integer(Integer.parseInt(second)));
				break;
			
			case "ON":
				p.put(Tag.comON, new Integer(Integer.parseInt(second)));
				break;
			
			case "OFF":
				p.put(Tag.comOFF, new Integer(Integer.parseInt(second)));
				break;
			
			case "SAVE":
				p.put(Tag.comSave, new String(second));
				break;
			
			case "LOAD":
				p.put(Tag.comLoad, new String(second));
				break;
			
			case "LOADA":
				p.put(Tag.comLoadA, new String(second));
				break;
			default: System.err.println("Error! Command name not recognized!");
		}
		
		this.commandQueue.add(p);
	}
	
	// Graphics
	@Override
	public void updateView(Packet p) {
		super.updateViewData(p);
		
		// graphics stuff; change what is displayed
	}
	
	private void initialize() {
		// graphics stuff; initialization 
	}
	
	// Constructor and graphics initialization
	public TextUI() {
		super();
		this.initialize();
	}
}
