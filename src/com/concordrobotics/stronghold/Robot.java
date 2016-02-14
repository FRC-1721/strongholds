
package com.concordrobotics.stronghold;

import com.concordrobotics.log.RobotLogger;
import com.concordrobotics.stronghold.commands.DriveInTeleop;
import com.concordrobotics.stronghold.subsystems.DriveTrain;
//import com.concordrobotics.stronghold.subsystems.NavxController;
import com.concordrobotics.stronghold.subsystems.Shooter;
//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
//import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
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
	Command driveInTeleop;
	/**
	 * Triggered when the robot is first initialized.
	 */
    public void robotInit() {
    	//Init logger
    	//This must come first. Different things call the logger!
//    	RobotLogger.init();
//    	RobotLogger.log("Robot Booting...", "BOOT");
		//Init Subsystems
//    	RobotLogger.log("Initializing sub systems...", "BOOT");
		RobotMap.driveTrain = new DriveTrain();
		RobotMap.shooter = new Shooter();
    	//Init OI
		RobotMap.oi = new OI();
		//Init motors
//		RobotLogger.log("Initializing motors...", "BOOT");
		RobotMap.dtLeft = new VictorSP(RobotMap.spLeftPort);
		RobotMap.dtRight = new VictorSP(RobotMap.spRightPort);
		RobotMap.dtLeftEnc = new Encoder(RobotMap.dtLeftEncPortA, RobotMap.dtLeftEncPortB, RobotMap.dtLeftEncReversed);
		RobotMap.dtRightEnc = new Encoder(RobotMap.dtRightEncPortA, RobotMap.dtRightEncPortB, RobotMap.dtRightEncReversed);
		RobotMap.shootL = new VictorSP(RobotMap.spShootLP);
		RobotMap.shootR = new VictorSP(RobotMap.spShootRP);
		RobotMap.shootA = new VictorSP(RobotMap.spShootAP);
		RobotMap.shootK = new Servo(RobotMap.spShootKP);
		/*
		//Gyro
		RobotLogger.log("Attempting communication with Gyro via MXP", "BOOT");
        try {
            RobotMap.navx = new AHRS(SPI.Port.kMXP); 
        } catch (RuntimeException ex ) {
        	RobotLogger.log("Gyro connection FAIL", "BOOT");
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        RobotMap.navController = new NavxController("NavController", RobotMap.navP, RobotMap.navI, RobotMap.navD,
        		RobotMap.navF, RobotMap.navx);*/
		RobotMap.robotDrive = new CustomRobotDrive(RobotMap.dtLeft, RobotMap.dtRight, RobotMap.dtLeftEnc, RobotMap.dtRightEnc/*(, RobotMap.navController*/);
		RobotMap.driveTrain = new DriveTrain();
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
    	driveInTeleop = new DriveInTeleop();
    	
    }

    /**
     * Loops during teleop.
     */    
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        RobotMap.driveTrain.cSwith(RobotMap.oi.jLeft.getTrigger(), RobotMap.oi.jLeft.getTrigger());
        RobotMap.driveTrain.jInput(RobotMap.oi.jLeft, RobotMap.oi.jRight);
    }

    /**
     * Loops during test.
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
