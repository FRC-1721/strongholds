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
	private Encoder m_leftEncoder;
	private Encoder m_rightEncoder;
	private boolean m_useGyro;
	private final double kMetersToFeet = 3.28084;
	double pidOut = 0.0;;
	
    // Initialize your subsystem here
    public DistanceDrivePID(double p, double i, double d) {
    	super("DistanceDrive",p,i,d);
    	m_leftEncoder = RobotMap.dtLeftEnc;
    	m_rightEncoder = RobotMap.dtRightEnc;
    	m_useGyro = false;
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void useGyro(boolean useGyro) {
    	m_useGyro = useGyro;
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	if (m_useGyro) {
    		return RobotMap.navx.getDisplacementX()*kMetersToFeet;
    	} else {
    		return 0.5*(m_leftEncoder.getDistance() + m_rightEncoder.getDistance());
    	}
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
