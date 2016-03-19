package com.concordrobotics.stronghold.subsystems;


import com.concordrobotics.stronghold.CustomPIDSubsystem;
import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.CustomPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Encoder;

/**
 *
 */
public class DistanceDrivePID extends CustomPIDSubsystem {
	double pidOut = 0.0;;
	
    // Initialize your subsystem here
    public DistanceDrivePID(double p, double i, double d) {
    	super("DistanceDrive",p,i,d);
    	
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
    	return  Robot.driveTrain.getDistance();
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
    	SmartDashboard.putNumber("distanceDriveSetpoint", this.getSetpoint());
    	SmartDashboard.putNumber("DistanceDriveDistance", this.returnPIDInput());
    	SmartDashboard.putNumber("DistanceDriveOutput", pidOut);
    	SmartDashboard.putNumber("DistanceDriveAvgError", controller.getAvgError());
    	SmartDashboard.putNumber("DistanceDriveIters", controller.getIterOnTarget());
    }
}
