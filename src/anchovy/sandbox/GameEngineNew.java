package anchovy.sandbox;

import java.util.ArrayList;
import java.util.HashMap;

import anchovy.sandbox.component.Component;
import anchovy.io.MainWindow;
import anchovy.util.Packet;
import anchovy.util.Tag;

public class GameEngineNew {
	protected HashMap<String, Component> components = null;
	protected MainWindow window;
	
	/**
	 * Default constructor; allocates memory for `window` and `components`
	 */
	public GameEngineNew() {
		components = new HashMap<String, Component>();
		//window = new MainWindow();
	}
	
	public void setupComponents(ArrayList<Packet> packets) {
		for(Packet p : packets) {
			if(p.containsTag(Tag.ioComponent));
				//GEcomponents.add(new Component(p));
		}
	}
	
	// Util
	public void addComponent(Component c) {
		this.components.put(c.name(), c);
	}
	
	
	/**
	 * TODO write comment
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GameEngineNew GE = new GameEngineNew();
		
		// initialize UI
		// (optional) load file
		// loop
		// - get command
		// - perform operations
		// - update viw
		// - pause 250 ms
	}
}
