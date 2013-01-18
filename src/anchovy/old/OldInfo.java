package anchovy.old;

import anchovy.Components.Component;

public class OldInfo {
	
	private String componentName;
	
	private Float temperature;
    private Float pressure;
    private Float controlRodLevel;
    private Float waterLevel;
    private Float RPM;
    private Boolean position;
    
    private Float failureTime;
    private Float outputFlowRate;
    
	private Component[] outputsTo;
	private Component[] recievesInputFrom;
	
	public OldInfo(	String componentName,
					Float temperature,
    				Float pressure,
    				Float controlRodLevel,
    				Float waterLevel,
    				Float RPM,
    				Boolean position,
    				Float failureTime,     
    				Component[] outputsTo,
					Component[] recievesInputFrom)
	{
		if(componentName == null){throw new NullPointerException("The component this Info is about MUST have a name!");}
		
		this.temperature = temperature;
		this.pressure = pressure;
		this.controlRodLevel = controlRodLevel;
		this.waterLevel = waterLevel;
		this.RPM = RPM;
		this.position = position;
		this.componentName = componentName;
		this.failureTime = failureTime;
		
		this.outputsTo = outputsTo;
		this.recievesInputFrom = recievesInputFrom;
		
	}
		
	@Override
	public String toString() {
		String s = "Stats for " + componentName + "\n\t";
		if (temperature != null){ 
			s = s + "Temperature: \t" + temperature + "\n\t";
		}
		if (pressure != null){
			s = s + "Pressure: \t" + pressure + "\n\t";
		}
		if (controlRodLevel != null){
			s = s + "Ctrl Rod Level: " + controlRodLevel + "\n\t";
		}
		if (waterLevel != null){
			s = s + "Water Level: \t" + waterLevel + "\n\t";
		}
		if (RPM != null){
			s = s + "RPM: \t\t" + RPM + "\n\t";
		}
		if (position != null){
			if(position == false){
				s = s + "Position \t" + "Closed" + "\n\t";
			}else{
				s = s + "Position \t" + "Open" + "\n\t";
			}
		}
		s = s + "\n";
		return s;
	}



	/*
 *  Getters and setters...
 *  there are a lot of them
 *  The setters may not be needed (only the constructor may need to do any setting,
 *  however the getters will be be needed for saving the game).
 */
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	public Float getPressure() {
		return pressure;
	}
	public void setPressure(Float pressure) {
		this.pressure = pressure;
	}
	public Float getControlRodLevel() {
		return controlRodLevel;
	}
	public void setControlRodLevel(Float controlRodLevel) {
		this.controlRodLevel = controlRodLevel;
	}
	public Float getWaterLevel() {
		return waterLevel;
	}
	public void setWaterLevel(Float waterLevel) {
		this.waterLevel = waterLevel;
	}
	public Float getRPM() {
		return RPM;
	}
	public void setRPM(Float rPM) {
		RPM = rPM;
	}
	public Boolean getPosition() {
		return position;
	}
	public void setPosition(Boolean position) {
		this.position = position;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public Float getFailureTime() {
		return failureTime;
	}
	public void setFailureTime(Float failureTime) {
		this.failureTime = failureTime;
	}
	public Component[] getOutputsTo() {
		return outputsTo;
	}
	public void setOutputsTo(Component[] outputsTo) {
		this.outputsTo = outputsTo;
	}
	public Component[] getRecievesInputFrom() {
		return recievesInputFrom;
	}
	public void setRecievesInputFrom(Component[] recievesInputFrom) {
		this.recievesInputFrom = recievesInputFrom;
	}
	public Float getOutputFlowRate(){
		return outputFlowRate;
	}
	public void setOutputFlowRate(Float outputFlowRate){
		this.outputFlowRate = outputFlowRate;
	}

	public static void main(String[] args)
	{
		OldInfo i = new OldInfo("A component",(float) 1,new Float(2),(float) 3,(float) 4,(float) 5, true, (float) 6, null, null);
		System.out.println(i.toString());
	}
}

