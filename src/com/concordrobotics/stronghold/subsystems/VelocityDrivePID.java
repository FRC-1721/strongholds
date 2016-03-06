package com.concordrobotics.stronghold.subsystems;


import com.concordrobotics.stronghold.CustomPIDSubsystem;
import com.concordrobotics.stronghold.PositionEstimator;
import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.CustomPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 *
 */
public class VelocityDrivePID extends CustomPIDSubsystem {
	private PositionEstimator pe;
	double pidOut = 0.0;
	
    // Initialize your subsystem here
    public VelocityDrivePID(double p, double i, double d, double f) {
    	super("DistanceDrive",p,i,d, f);
    	pe = Robot.positionEstimator;
    	setPIDSourceType(PIDSourceType.kRate);
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return pe.getVelocityS();
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	pidOut = output;
    }
    
    public void reset() {
    	controller.reset();
    }
    
    public double getPIDOutput() {
    	return pidOut;
    }
    
    public void updateSmartDashboard() {
    	SmartDashboard.putNumber("velDriveSetpoint", this.getSetpoint());
    	SmartDashboard.putNumber("velDriveRate", this.returnPIDInput());
    	SmartDashboard.putNumber("velDriveOutput", pidOut);
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
