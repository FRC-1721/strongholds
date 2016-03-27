package com.concordrobotics.stronghold;

import edu.wpi.first.wpilibj.AnalogInput;

public class CustomUltrasonic {
	public AnalogInput aInput;
	protected static final double kScale = 512.0/12.0/5.0;
	
	public CustomUltrasonic(int aChannel) {
		aInput = new AnalogInput(aChannel);
		// TODO Auto-generated constructor stub
	}
	
	public double getDistance () {
		return kScale*aInput.getVoltage();
	}
	

}
