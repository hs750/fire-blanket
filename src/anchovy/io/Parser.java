package anchovy.io;

import java.io.FileNotFoundException;
import java.util.StringTokenizer;

import anchovy.GameEngine;
import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.*;
/**
 * This class is responsible for parsing text commands supplied by the user and then executing a fitting gameEngine method
 * or interacting with a certain component
 * Valid commands are: save; save as; show saves; load; valveName open/close
 * pumpName on/off;
 * Control Rods name lower/raise;
 * componentName repair
 * 
 */
public class Parser {
	private GameEngine engine;

	public Parser(GameEngine Engine)
	{
		engine = Engine;
	}

	/**
	 * Using the two input string determines what command should be executed by the gameEngine or 
	 * one of the components
	 * @command command Command that will applied to a component
	 * @param componentName part of the input supplied by the user
	 * @return returns a message to the user that gets written into the output of the console
	 */
	public String parseCommand(String componentName, String command) throws FileNotFoundException 
			{
		String result = new String();
		//Check for for engine commands first
		if (componentName.contains("save as")) {
			return engine.saveGameState(engine.getAllComponentInfo(), command);
		} else if (componentName.equals("save")) {
			return engine.save(engine.getAllComponentInfo());
			
		} else if (componentName.contains("load")) {
			engine.readfile(command);
			engine.updateInterfaceComponents(engine.getAllComponentInfo());

		} else if (componentName.equals("show saves")) {
			return engine.findAvailableSaves();
		}
		//then check for components
		try {
			Component component = engine.getPowerPlantComponent(componentName);
			InfoPacket i = new InfoPacket();
			if (component.getName().contains("Valve")) 
			{
				// i.namedValues.add(new Pair<String>(Pair.Label.cNme,
				// component.getName()));
				if (command.equals("open")) {
					i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
				} else if (command.equals("close")) {
					i.namedValues
							.add(new Pair<Boolean>(Pair.Label.psit, false));
				}
			} else if (component.getName().contains("Pump")) {
				//i.namedValues.add(new Pair<String>(Pair.Label.cNme, component
				//		.getName()));
				if (command.equals("on")) {
					i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
				} else if (command.equals("off")) {
					i.namedValues
							.add(new Pair<Boolean>(Pair.Label.psit, false));
				}
			}

			else if (component.getName().contains("Control rods")) {
				//i.namedValues.add(new Pair<String>(Pair.Label.cNme, component
				//		.getName()));
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
		}
		catch(Exception e)
		{
			result = "Component " + componentName + " does not exist";
		}

		engine.updateInterfaceComponents(engine.getAllComponentInfo());
		return result;
	}
	
	/**
	 * Checks for bad input and splits the string in two tokens, then calls parseCommand.
	 * @param in an input string (usually coming from the UI)
	 * @return a message for the user
	 * @throws FileNotFoundException
	 */
	public String parse(String text) throws FileNotFoundException {
		if(text.length() != 0)
		{
			String lowerCase= text;
			if(lowerCase.equals("save"))
			{
				return parseCommand(lowerCase,lowerCase);
			}
			//String lowerCase = text.toLowerCase(); //Convert string to lower case to avoid case mismatches
			
			if(lowerCase.contains(" "))
			{
				if(lowerCase.equals("show saves") || lowerCase.equals("save as"))
					return parseCommand(lowerCase, lowerCase);
			String s= lowerCase.substring(0, lowerCase.lastIndexOf(' '));
			String i= lowerCase.substring(lowerCase.lastIndexOf(' ') + 1, lowerCase.length());


			return parseCommand(s,i);
			}
			
			else return "Invalid command";
		}

		else return "";
	}
}
