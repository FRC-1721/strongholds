package com.concordrobotics.stronghold.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;

public class CustomPIDOutput implements PIDOutput {
	
	double out;
	
	@Override
	public void pidWrite(double output) {
		out = output;
	}

	public double pidGet() {
		return out;
	}
	
	
}
