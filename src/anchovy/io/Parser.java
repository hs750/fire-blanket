package anchovy.io;

import java.io.FileNotFoundException;
import java.util.StringTokenizer;

import anchovy.GameEngine;
import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.*;

class Parser {
	private GameEngine engine;

	public Parser(GameEngine Engine) {
		engine = Engine;
	}

	public String parseCommand(String componentName, String command)
			throws FileNotFoundException {
		String result = new String();
		Component component = engine.getPowerPlantComponent(componentName);
		InfoPacket i = new InfoPacket();

		if (component != null) {

			if (component.getName().contains("Valve")) {
				// i.namedValues.add(new Pair<String>(Pair.Label.cNme,
				// component.getName()));
				if (command.equals("open")) {
					i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
				} else if (command.equals("close")) {
					i.namedValues
							.add(new Pair<Boolean>(Pair.Label.psit, false));
				}
			} else if (component.getName().contains("Pump")) {
				i.namedValues.add(new Pair<String>(Pair.Label.cNme, component
						.getName()));
				if (command.equals("on")) {
					i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
				} else if (command.equals("off")) {
					i.namedValues
							.add(new Pair<Boolean>(Pair.Label.psit, false));
				}
			}

			else if (component.getName().contains("Control rods")) {
				i.namedValues.add(new Pair<String>(Pair.Label.cNme, component
						.getName()));
				if (command.equals("lower")) {
					i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
				} else if (command.equals("raise")) {
					i.namedValues
							.add(new Pair<Boolean>(Pair.Label.psit, false));
				}
			} else if (command.equals("repair")) {
				engine.repair(component);
			}
			try {
				component.takeInfo(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (componentName.contains("save as")) {
			return engine.saveGameState(engine.getAllComponentInfo(), command);
		} else if (componentName.equals("save")) {

			return engine.save(engine.getAllComponentInfo());
		} else if (componentName.contains("load")) {
			engine.readfile(command);
			engine.updateInterfaceComponents(engine.getAllComponentInfo());

		} else if (componentName.equals("show saves")) {
			return engine.findAvailableSaves();
		}

		else

		{
			result = "Component " + componentName + " does not exist";
		}

		engine.updateInterfaceComponents(engine.getAllComponentInfo());
		return result;
	}

	public String parse(String in) throws FileNotFoundException {
		if (in.length() == 0 || in.contains(" ") == false)
			return "";
		
		String inLower = in.toLowerCase();
		
		if (inLower.equals("save")) {
			return parseCommand(inLower, inLower);
		}
		
		if (inLower.equals("show saves")) {
			return parseCommand(inLower, inLower);
		}
		
		StringTokenizer st = new StringTokenizer(inLower, " \t,;'-()");
		
		return parseCommand(st.nextToken(), st.nextToken());
	}
}