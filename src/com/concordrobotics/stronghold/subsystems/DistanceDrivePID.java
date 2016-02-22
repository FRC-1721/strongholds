package com.concordrobotics.stronghold.subsystems;


import com.concordrobotics.stronghold.CustomPIDSubsystem;
import com.concordrobotics.stronghold.CustomPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Encoder;

/**
 *
 */
public class DistanceDrivePID extends CustomPIDSubsystem {
	Encoder m_leftEncoder;
	Encoder m_rightEncoder;
	double pidOut = 0.0;;
	
    // Initialize your subsystem here
    public DistanceDrivePID(double p, double i, double d) {
    	super("DistanceDrive",p,i,d);
    	m_leftEncoder = RobotMap.dtLeftEnc;
    	m_rightEncoder = RobotMap.dtRightEnc;
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return 0.5*(m_leftEncoder.getDistance() + m_rightEncoder.getDistance());
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	pidOut = output;
    }
    
    public void reset() {
    	CustomPIDController t_controller = this.getPIDController();
    	t_controller.reset();
    }
    
    public double getPIDOutput() {
    	return pidOut;
    }
    
    public void updateSmartDashboard() {
    	CustomPIDController t_pidController = this.getPIDController();
    	SmartDashboard.putNumber("distanceDriveSetpoint", this.getSetpoint());
    	SmartDashboard.putNumber("DistanceDriveDistance", this.returnPIDInput());
    	SmartDashboard.putNumber("DistanceDriveOutput", pidOut);
    	SmartDashboard.putNumber("DistanceDriveAvgError", t_pidController.getAvgError());
    }
}
