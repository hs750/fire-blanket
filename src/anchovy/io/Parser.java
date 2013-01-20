package anchovy.io;

import java.io.FileNotFoundException;
import java.util.StringTokenizer;

import anchovy.GameEngine;
import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Components.*;
import anchovy.Pair.Label;
/**
 * This class is responsible for parsing text commands supplied by the user and then executing a fitting gameEngine method
 * or interacting with a certain component
 * Valid commands are: save; save as; show saves; load; valveName open/close
 * pumpName RPM/rpm number;
 * Control Rods name set/SET number;
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
	public String parseCommand(String componentName, String command)
		{
		String result = new String();
		//Check for for engine commands first
		if (componentName.contains("save as")) {
			return engine.saveGameState(engine.getAllComponentInfo(), command);
		} else if (componentName.equals("save")) {
			return engine.save(engine.getAllComponentInfo());
			
		} else if (componentName.contains("load")) 
		{
			
			try {
				engine.readfile(command);
			} catch (FileNotFoundException e) {
				return "File not found.";
			}
			engine.updateInterfaceComponents(engine.getAllComponentInfo());

		} else if (componentName.equals("show saves")) {
			return engine.findAvailableSaves();
		}
		//then check for components
		try {
			InfoPacket i = new InfoPacket();
			Component component = null;
			
			//Used for components that also need a numerical parameter (pumps and control rods for now)
			if (componentName.contains("Pump") || componentName.contains("rods")) 
			{
				String alterName = componentName.substring(0, componentName.lastIndexOf(' '));
				component = engine.getPowerPlantComponent(alterName);
				if (componentName.contains("RPM") || componentName.contains("rpm")) 
				{
					i.namedValues.add(new Pair<Double>(Label.RPMs, Double.parseDouble(command)));
				} else if(componentName.contains("set") || componentName.contains("SET")) 
				{
					i.namedValues.add(new Pair<Double>(Label.coRL, Double.parseDouble(command)));
				}
			} 
			else
			{
				component = engine.getPowerPlantComponent(componentName);
				
				if (component.getName().contains("Valve")) 
				{
					// i.namedValues.add(new Pair<String>(Pair.Label.cNme,
					// component.getName()));
					if (command.equals("open")) {
						i.namedValues.add(new Pair<Boolean>(Pair.Label.psit, true));
					} else if (command.equals("close")) 
					{
						i.namedValues
								.add(new Pair<Boolean>(Pair.Label.psit, false));
					}
				} 
	
				else if (command.equals("repair")) 
				{
					engine.repair(component);
				}
			}
			
			try {
				component.takeInfo(i);
			} catch (Exception e) {
				return e.getMessage();
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
	public String parse(String text)
	{
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
