
package com.concordrobotics.stronghold;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Concord Robotics FRC Team 1721
 * 2016 - FIRST STRONGHOLD
 * Robot.java
 * 
 * This is the main class for the entire robot.
 * The VM is set to run this class before anything else.
 */
public class Robot extends IterativeRobot {

	public static OI oi;

	/**
	 * Triggered when the robot is first initialized.
	 */
    public void robotInit() {
		oi = new OI();
		RobotMap.dtLeft = new VictorSP(RobotMap.spLeftPort);
		RobotMap.dtRight = new VictorSP(RobotMap.spRightPort);
		RobotMap.tank = new RobotDrive(RobotMap.dtLeft, RobotMap.dtRight);
		RobotMap.arcade = new RobotDrive(RobotMap.dtLeft, RobotMap.dtRight);
    }
	
    /**
     * Triggered when the robot is disabled (every time).
     */
    public void disabledInit(){

    }
	
    /**
     * Loops while the robot is disabled.
     */
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/**
	 * Triggers when auto starts.
	 */
    public void autonomousInit() {
    }
    
    /**
     * Loops during auto.
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * Triggers when teleop starts.
     */
    public void teleopInit() {
    }

    
    /**
     * Loops during teleop.
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * Loops during test.
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
