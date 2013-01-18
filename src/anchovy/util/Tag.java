package anchovy.util;

public enum Tag {
	// General
	name,						// String
	intVal,						// Used in places like TextUI
	
	// Component header - see ComponentNew.java
	compName,					// (String)
	compType,					// (String)
	compOutput,					// (String) part of Component.outputsTo
	compInput,					// (String) part of Component.receivesInputFrom
	failTime,					// (Double)
	flowRate,					// (Double)
	
	// Component
	compTemperature,			// (Double)
	compPressure,				// (Double)
	compWaterLevel,				// (Double)
	compElectricity,			// (Double)
	compRPM,					// (Double)
	compControlRodLevel,		// (Double)
	compPosition,				// (Boolean)
	
	
	// UserInterface readings
	reactorTemp,
	reactorPres,
	reactorLevel,
	condenserTemp,
	condenserPres,
	condenserLevel,
	userMessage,
	
	// TextUI commands
	comLower,
	comRaise,
	comOpen,
	comClose,
	comON,
	comOFF,
	comSave,
	comLoad,
	comLoadA,
	extraVariable,
	
	// Save/Load
	ioCurDir,
	ioPath,
	ioPathA,
	ioComponent,				// Indicates that a Packet contains a (full) Component description
	
	// Testing purposes
	test1,
	test2,
	test3,
	test4;
}
