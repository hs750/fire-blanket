package anchovy;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import anchovy.Components.*;
import anchovy.Pair.Label;
import anchovy.io.*;

/**
 * Game Engine for the 'Nuclear Power Plant Simulation Game' Links all the
 * technical components of the game together - the 'Controller' in the MVC
 * design
 * 
 * @author Harrison
 */
public class GameEngine {
	public ArrayList<Component> powrPlntComponents = null;
	// Parser parser;
	// UI ui = null; Commented out for now
	MainWindow window;

	/**
	 * Constructor for the game engine On creation it creates a list to store
	 * the components of the power plant and links to a user interface (what
	 * ever type of user interface that may be)
	 */
	public GameEngine() {
		powrPlntComponents = new ArrayList<Component>();
		// ui = new UI(this); Commented out for now
		// parser = new Parser(this);

		window = new MainWindow(this);

		/*
		 * final Timer gameLoop = new Timer(); gameLoop.scheduleAtFixedRate(new
		 * TimerTask(){ boolean stop = false; long timesRoundLoop = 0;
		 * 
		 * public void run(){ timesRoundLoop++; if(timesRoundLoop > 10){ stop =
		 * true; } if(!stop){ System.out.println("Hello"); runSimulation();
		 * }else{ gameLoop.cancel(); }
		 * 
		 * } }, 0, 1000);
		 */
	}

	public void repair(Component component) {
		component.repair();
	}

	/**
	 * Using a list of Info Packets (generated from loading the same from file
	 * or elsewhere) Adds each of the components described in the Info Packet
	 * list to the list of components in the power plant Then sends the info
	 * packet to that component to initialize all its values Once all components
	 * of the power plant are in the list and initialized, they are then all
	 * connected together in the way described by the info packets.
	 * 
	 * @param allPowerPlantInfo
	 *            A list of info packets containing all the information about
	 *            all components to be put into the power plant.
	 */
	public void setupPowerPlantConfiguration(
			ArrayList<InfoPacket> allPowerPlantInfo) {
		Iterator<InfoPacket> infoIt = allPowerPlantInfo.iterator();
		InfoPacket currentInfo = null;
		String currentCompName = null;
		Component currentNewComponent = null;

		// Create component list.
		while (infoIt.hasNext()) {
			currentInfo = infoIt.next();
			currentCompName = getComponentNameFromInfo(currentInfo);

			// Determine component types we are dealing with.
			if (currentCompName.contains("Condenser")) {
				currentNewComponent = new Condenser(currentCompName);
			} else if (currentCompName.contains("Generator")) {
				currentNewComponent = new Generator(currentCompName);
			} else if (currentCompName.contains("Pump")) {
				currentNewComponent = new Pump(currentCompName);
			} else if (currentCompName.contains("Reactor")) {
				currentNewComponent = new Reactor(currentCompName);
			} else if (currentCompName.contains("Turbine")) {
				currentNewComponent = new Turbine(currentCompName);
			} else if (currentCompName.contains("Valve")) {
				currentNewComponent = new Valve(currentCompName);
			}
			addComponent(currentNewComponent); // add the component to the power
												// plant

			try {
				assignInfoToComponent(currentInfo); // send the just added
													// component its info.
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Connect components together
		infoIt = allPowerPlantInfo.iterator();// reset the iterator TODO i think
												// this works.
		ArrayList<String> inputComponents = new ArrayList<String>();
		ArrayList<String> outputComponents = new ArrayList<String>();

		Iterator<Pair<?>> pairIt = null;
		Pair<?> currentPair = null;
		Label currentLabel = null;

		Component currentComponent = null;
		//Iterator<Component> compIt = null;

		Iterator<String> connectionNameIt = null;
		Component attachComp = null;

		// get info for each components
		while (infoIt.hasNext()) {
			currentInfo = infoIt.next();
			pairIt = currentInfo.namedValues.iterator();

			// get the useful information out of the info.
			while (pairIt.hasNext()) {
				currentPair = pairIt.next();
				currentLabel = currentPair.getLabel();

				switch (currentLabel) {
				case cNme:
					currentCompName = (String) currentPair.second();
					break;
				case rcIF:
					inputComponents.add((String) currentPair.second());
					break;
				case oPto:
					outputComponents.add((String) currentPair.second());
					break;
				default:
					break;
				}
			}

			// Get the component that we are going to connect other components
			// to.
			currentComponent = getPowerPlantComponent(currentCompName);

			// Attach each input component to the current component.
			connectionNameIt = inputComponents.iterator();
			while (connectionNameIt.hasNext()) {
				attachComp = getPowerPlantComponent(connectionNameIt.next());
				connectComponentTo(currentComponent, attachComp, true);
			}
			// Attach each output component to the current compoennt
			connectionNameIt = outputComponents.iterator();
			while (connectionNameIt.hasNext()) {
				attachComp = getPowerPlantComponent(connectionNameIt.next());
				connectComponentTo(currentComponent, attachComp, false);
			}

		}
	}

	/**
	 * Using the name of a component in the format of a string returns the
	 * actual Component found in the list of components of the Power Plant
	 * 
	 * @param The
	 *            name of a component.
	 * @return The component specified by the given name.
	 */
	public Component getPowerPlantComponent(String currentCompName) {
		Component currentComponent = null;
		Iterator<Component> compIt;
		compIt = powrPlntComponents.iterator();
		Component c = null;
		String cName = null;
		while (compIt.hasNext()) {
			c = compIt.next();
			cName = c.getName();
			if (cName.equals(currentCompName)) {
				currentComponent = c;
			}
		}
		return currentComponent;
	}

	/**
	 * Extracts the first component name out of an info packet.
	 * 
	 * @param info
	 *            An info packet for a component
	 * @return The component name contained within the given info packet.
	 */
	private String getComponentNameFromInfo(InfoPacket info) {
		Iterator<Pair<?>> pairIt = info.namedValues.iterator();
		Pair<?> pair = null;
		String name = null;
		while (pairIt.hasNext() && name == null) {
			pair = pairIt.next();
			if (pair.getLabel() == Label.cNme) {
				name = (String) pair.second();
			}
		}
		return name;

	}

	/**
	 * Sends an info packet to a component the components is specified by the
	 * name of the component in the info packet.
	 * 
	 * @param info
	 *            Info Packet to be sent to a component
	 */
	public void assignInfoToComponent(InfoPacket info) throws Exception {
		String compToSendTo = null;

		// Pair<?> pair = null;
		// Iterator<Pair<?>> pi = info.namedValues.iterator();
		// Label label = null;
		// while(pi.hasNext() && compToSendTo == null){
		// pair = pi.next();
		// label = pair.getLabel();
		// switch (label){
		// case cNme:
		// compToSendTo = (String) pair.second();
		// default:
		// break;
		// }
		// }

		compToSendTo = getComponentNameFromInfo(info);

		// Iterator<Component> ci = powrPlntComponents.iterator();
		// boolean comNotFound = true;
		Component com = null;
		// while(ci.hasNext() && comNotFound){
		// comNotFound = true;
		// com = ci.next();
		// if(com.getName() == compToSendTo){
		// comNotFound = false;
		//
		// }
		// }

		com = getPowerPlantComponent(compToSendTo);
		/*
		 * if the component wasn't found throw an exception stating this
		 */
		if (com == null) {
			throw new Exception(
					"The component you were trying to send info to doesn't exit: " + compToSendTo);
		} else {
			com.takeInfo(info);
		}
	}

	/**
	 * Goes through the list of components one by one calling its simulate
	 * method This should be called in a loop to get a continuous simulation.
	 */
	public void runSimulation() {
		Iterator<Component> ci = powrPlntComponents.iterator();
		Component comp = null;
		while (ci.hasNext()) {
			comp = ci.next();
			comp.calculate();
		}
	}

	/**
	 * Add a component to the list of components
	 * 
	 * @param component
	 *            the component to be added to the list of components
	 */
	public void addComponent(Component component) {
		powrPlntComponents.add(component);
	}

	/**
	 * Connect two components together.
	 * 
	 * @param comp1
	 *            the component that we are working with
	 * @param comp2
	 *            the component that will be added to comp1
	 * @param input_output
	 *            denoted whether it is an input or an output; in = true, out =
	 *            false
	 */
	public void connectComponentTo(Component comp1, Component comp2,
			boolean input_ouput) {
		if (input_ouput) {
			comp1.connectToInput(comp2);
			comp2.connectToOutput(comp1);
		} else {
			comp1.connectToOutput(comp2);
			comp2.connectToInput(comp1);

		}
	}
	/**
	 * 
	 * @param packets
	 * @param FileName the name of the file the data will be saved to
	 * @return Returns a string that will be written into the console output
	 */
	public String saveGameState(ArrayList<InfoPacket> packets, String FileName) {
		String output = new String();
		fileName = FileName;
		Iterator<InfoPacket> packetIter = packets.iterator();
		InfoPacket pckt = null;
		while (packetIter.hasNext()) {

			pckt = packetIter.next();
			Iterator<Pair<?>> namedValueIter = pckt.namedValues.iterator();
			while (namedValueIter.hasNext()) {
				Pair<?> pair = namedValueIter.next();

				output += pair.first() + '/' + pair.second().toString() + '\n';
			}
			
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("saves/" + FileName + ".fg"));
			out.write(output);
			System.out.println(output);
			out.close();
		} catch (IOException e) {
			System.out.println("Exception ");
		}
		return "File saved";

	}

	public void readfile(String file) throws FileNotFoundException {
		// FileReader fr=new FileReader(path);
		// BufferedReader br=new BufferedReader(fr);
		String path = new java.io.File("").getAbsolutePath() + "/saves/";
		FileInputStream fstream = new FileInputStream(path + file + ".fg");
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		clearPowerPlant(); 
		ArrayList<String> data = new ArrayList<String>();
		
		ArrayList<InfoPacket> infoList = new ArrayList<InfoPacket>();
		String temp;
		int c=0;
		try {
			while ((temp = br.readLine()) != null) {
				
				data.add(temp);	
				if(temp.contains("Name of component"))
				{
					c++;
				}
			}
			br.close();
			
			int i = 0;
			String textData[]=new String[data.size()];	
			
			
			textData= data.toArray(new String[i]);
			InfoPacket info = new InfoPacket();
			while (i<data.size()-1) {
				
				String ch = textData[i].substring(0, textData[i].indexOf("/"));
				
				if (ch.equals("Name of component")) {
					if(i != 0)
					{
						infoList.add(info);
						info = new InfoPacket();
					}
					String c1 = textData[i].substring(textData[i].indexOf("/")+1,
							textData[i].length());
					info.namedValues.add(new Pair<String>(Label.cNme, c1));
					System.out.println(ch + "=" + c1);
				}

				else if (ch.equals("FailuerTime")) {
					String d = textData[i].substring(
							textData[i].indexOf("/") + 1, textData[i].length());
					Float i1 = Float.parseFloat(d);
					info.namedValues.add(new Pair<Float>(Label.falT, i1));
					System.out.println(ch + "=" + i1);
				} else if (ch.equals("Output flow rate"))

				{
					String d = textData[i].substring(
							textData[i].indexOf("/") + 1, textData[i].length());
					Float i1 = Float.parseFloat(d);
					info.namedValues.add(new Pair<Float>(Label.OPFL, i1));
					System.out.println(ch + "=" + i1);
				} else if (ch.equals("Position")) {
					String d = textData[i].substring(textData[i].indexOf("/"),
							textData[i].length());
					boolean ok = Boolean.parseBoolean(d);
					info.namedValues.add(new Pair<Boolean>(Label.psit, ok));
					System.out.println(ch + "=" + ok);
				}
				else if (ch.equals("Outputs to"))
				{
				
					String d = textData[i].substring(textData[i].indexOf("/")+1,
							textData[i].length());
					
					info.namedValues.add(new Pair<String>(Label.oPto, d));
					System.out.println(ch + "=" + d);
				}
				else if (ch.equals("Recieves input from"))
				{
				
					String d = textData[i].substring(textData[i].indexOf("/")+1,
							textData[i].length());
					
					info.namedValues.add(new Pair<String>(Label.rcIF, d));
					System.out.println(ch + "=" + d);
				}
				i++;
				
			}
			infoList.add(info);
		} catch (IOException e) {
			System.out.println("Cannot load file");
		}
		setupPowerPlantConfiguration(infoList);

	}

	public String findAvailableSaves() 
	{
		String path = new java.io.File("").getAbsolutePath() + "/saves";
		String files;
		String result = new String();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.endsWith(".fg") || files.endsWith(".FG")) {
					result += files.substring(0, files.lastIndexOf('.')) + '\n';
				}
			}
		}
		return result;
	}

	/**
	 * Get all the info from all the components within the power plant. Used for
	 * saving and displaying info to UI.
	 * 
	 * @return List of InfoPackets for ALL components in the power plant.
	 */
	public ArrayList<InfoPacket> getAllComponentInfo() {
		ArrayList<InfoPacket> allInfo = new ArrayList<InfoPacket>();
		Iterator<Component> ci = powrPlntComponents.iterator();
		Component comp = null;
		while (ci.hasNext()) {
			comp = ci.next();
			allInfo.add(comp.getInfo());
		}
		return allInfo;

	}

	/**
	 * Resets the components of the power plant to am empty list. Will be needed
	 * for loading a power plant from file.
	 */
	public void clearPowerPlant() {
		powrPlntComponents = new ArrayList<Component>();
	}

	/**
	 * Updates interface with the latest changes to all the components.
	 * 
	 * @param packets
	 *            Info about all the components in the game
	 */
	public void updateInterfaceComponents(ArrayList<InfoPacket> packets) 
	{
		String nonmodifiable = new String();
		String modifiable = new String();
		Iterator<InfoPacket> packetIter = packets.iterator();
		InfoPacket pckt = null;
		// All info gathered about a component
		String componentName = new String();
		String componentDescriptionModi = new String();
		String componentDescriptionNon = new String();
		while (packetIter.hasNext()) {

			pckt = packetIter.next();
			Iterator<Pair<?>> namedValueIter = pckt.namedValues.iterator();
			while (namedValueIter.hasNext()) {
				Pair<?> pair = namedValueIter.next();
				Label currentLabel = pair.getLabel();

				switch (currentLabel) {
				case cNme:
					componentName = (String) pair.second() + '\n';
					break;
				/*case rcIF:
					componentDescriptionNon += "Gets inputs from: "
							+ pair.second().toString() + '\n';
					break;
				case oPto:
					componentDescriptionNon += "Outputs to: "
							+ pair.second().toString() + '\n';
					break;*/
				case temp:
					componentDescriptionNon += "Temperature: "
							+ pair.second().toString() + '\n';
					break;
				case pres:
					componentDescriptionNon += "Pressure: "
							+ pair.second().toString() + '\n';
					break;
				case coRL:
					componentDescriptionModi += "Control rod level: "
							+ pair.second().toString() + '\n';
					break;
				case wLvl:
					componentDescriptionNon += "Water level: "
							+ pair.second().toString() + '\n';
					break;
				case RPMs:
					componentDescriptionModi += "RPMs: "
							+ pair.second().toString() + '\n';
					break;
				case psit:
					componentDescriptionModi += "Position: "
							+ pair.second().toString() + '\n';
					break;
				case elec:
					componentDescriptionNon += "Electricity generated: "
							+ pair.second().toString() + '\n';
					break;
				case OPFL:
					componentDescriptionNon += "Output flow rate: "
							+ pair.second().toString() + '\n';
					break;
				default:
					break;
				}

			}
			if (componentDescriptionNon.length() != 0
					&& componentName.length() != 0)
				nonmodifiable += componentName + componentDescriptionNon;
			if (componentDescriptionModi.length() != 0
					&& componentName.length() != 0)
				modifiable += componentName + componentDescriptionModi;
			componentName = "";
			componentDescriptionNon = "";
			componentDescriptionModi = "";
		}
		window.updateLeftPanel(nonmodifiable);
		window.updateRightPanel(modifiable);

	}

	String fileName;
	/**
	 * Checks if a file name is already stored in the system (happens if the game was started using load or if save as command was previously used)
	 *  and can be used for further save games
	 * @param packets the state of the power plant that needs to be save
	 * @return returns what should be out put to the console
	 */
	public String save(ArrayList<InfoPacket> packets) {

		if (fileName != null) {
			saveGameState(packets, fileName);
			return "File saved";
		} else {
			return "Use save as command, because there is no valid filename";
		}

	}

	public static void main(String[] args) {
		// TODO create the main game loop
		GameEngine gameEngine = new GameEngine();
		ArrayList<InfoPacket> infoList = new ArrayList<InfoPacket>();

		InfoPacket info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Valve 1"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Valve 2"));
		info.namedValues.add(new Pair<String>(Label.oPto, "Valve 2"));
		infoList.add(info);

		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Valve 2"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<String>(Label.oPto, "Valve 1"));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Valve 1"));
		infoList.add(info);

		/*
=======
/*
>>>>>>> branch 'master' of https://github.com/ansimionescu/Fire-blanket.git
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Pump 1"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 5.00));
		info.namedValues.add(new Pair<String>(Label.oPto, "Valve 3"));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Condensor"));
		infoList.add(info);
		
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Valve 3"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<String>(Label.oPto, "Pump 1"));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Pump 1"));
		infoList.add(info);

		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Pump 2"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 5.00));
		info.namedValues.add(new Pair<String>(Label.oPto, "Valve 4"));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Condensor"));
		infoList.add(info);

		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Valve 4"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<String>(Label.oPto, "Pump 2"));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Pump 2"));
		infoList.add(info);

		// ///////////
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Condensor"));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<Double>(Label.temp, 10.00));
		info.namedValues.add(new Pair<Double>(Label.pres, 10.00));
		info.namedValues.add(new Pair<Double>(Label.wLvl, 10.00));
		info.namedValues.add(new Pair<String>(Label.oPto, "Pump 1"));
		info.namedValues.add(new Pair<String>(Label.oPto, "Pump 2"));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Coolant Pump"));
		// info.namedValues.add(new Pair<String>(Label.rcIF, "Turbine"));
		// //doesn't exist yet
		info.namedValues.add(new Pair<String>(Label.rcIF, "Valve 2"));
		infoList.add(info);

		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Coolant Pump"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 5.00));
		info.namedValues.add(new Pair<String>(Label.oPto, "Condensor"));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Coolant Pump"));
		infoList.add(info);
		// ///////////
*/
		gameEngine.clearPowerPlant();
		assert (gameEngine.getAllComponentInfo().isEmpty());

		gameEngine.setupPowerPlantConfiguration(infoList);
		gameEngine.updateInterfaceComponents(gameEngine.getAllComponentInfo());
		
		System.out.println("HellO");
	}
}
