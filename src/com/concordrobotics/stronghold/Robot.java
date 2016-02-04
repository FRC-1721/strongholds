
package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.subsystems.DriveTrain;
import com.concordrobotics.stronghold.subsystems.Shooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
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

	/**
	 * Triggered when the robot is first initialized.
	 */
    public void robotInit() {
    	//Init OI
		RobotMap.oi = new OI();
		//Init Subsystems
		RobotMap.driveTrain = new DriveTrain();
		RobotMap.shooter = new Shooter();
		//Init motors
		RobotMap.dtLeft = new VictorSP(RobotMap.spLeftPort);
		RobotMap.dtRight = new VictorSP(RobotMap.spRightPort);
		RobotMap.tank = new RobotDrive(RobotMap.dtLeft, RobotMap.dtRight);
		RobotMap.arcade = new RobotDrive(RobotMap.dtLeft, RobotMap.dtRight);
		RobotMap.shootL = new VictorSP(RobotMap.spShootLP);
		RobotMap.shootR = new VictorSP(RobotMap.spShootRP);
		RobotMap.shootA = new VictorSP(RobotMap.spShootAP);
		RobotMap.shootK = new Servo(RobotMap.spShootKP);
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
