
package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.subsystems.*;
import com.concordrobotics.stronghold.CustomRobotDrive.MotorType;
import com.concordrobotics.stronghold.commands.*;
import com.concordrobotics.stronghold.CustomPIDController;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.PIDSourceType;
import com.kauailabs.navx.frc.AHRS;

/**
 * Concord Robotics FRC Team 1721
 * 2016 - FIRST STRONGHOLD
 * Robot.java
 *
 * This is the main class for the entire robot.
 * The VM is set to run this class before anything else.
 */
public class Robot extends IterativeRobot {

	// Subsystems
	public static DriveTrain driveTrain;
	public static CustomRobotDrive robotDrive;
	public static Shooter shooter;
	public static OI oi;
	public static NavxController navController;
	
    public void robotInit() {
		//Init Subsystems
		shooter = new Shooter();

		//Init motor and controllers
		RobotMap.dtLeft = new VictorSP(RobotMap.spLeftPort);
		RobotMap.dtRight = new VictorSP(RobotMap.spRightPort);
		RobotMap.dtLeft.setInverted(true);
		LiveWindow.addActuator("LeftRobotDrive", "Victor", RobotMap.dtLeft);
		LiveWindow.addActuator("RightRobotDrive", "Victor", RobotMap.dtRight);
		RobotMap.dtLeftEnc = new Encoder(RobotMap.dtLeftEncPortA, RobotMap.dtLeftEncPortB, RobotMap.dtLeftEncReversed);	  
		RobotMap.dtRightEnc = new Encoder(RobotMap.dtRightEncPortA, RobotMap.dtRightEncPortB, RobotMap.dtRightEncReversed);
		RobotMap.dtLeftEnc.setDistancePerPulse(0.00825);
		RobotMap.dtRightEnc.setDistancePerPulse(0.0134);
		LiveWindow.addSensor("LeftRobotDrive", "Encoder", RobotMap.dtLeftEnc);
	    LiveWindow.addSensor("RightRobotDrive", "Encoder", RobotMap.dtRightEnc);
	    RobotMap.dtLeftController = new CustomPIDController(RobotMap.dtP, RobotMap.dtI, RobotMap.dtD, RobotMap.dtF,
	    												RobotMap.dtLeftEnc, RobotMap.dtLeft);
	    RobotMap.dtRightController = new CustomPIDController(RobotMap.dtP, RobotMap.dtI, RobotMap.dtD, RobotMap.dtF,
				RobotMap.dtRightEnc, RobotMap.dtRight);
	    
		//Shooter 
	    RobotMap.shootL = new VictorSP(RobotMap.spShootLP);
		RobotMap.shootR = new VictorSP(RobotMap.spShootRP);
		RobotMap.shootA = new VictorSP(RobotMap.spShootAP);
		RobotMap.shootK = new Servo(RobotMap.spShootKP);
		
		// Gyro and controller
        RobotMap.navx = new AHRS(SPI.Port.kMXP); 
        LiveWindow.addSensor("Gyro", "navx", RobotMap.navx);
        navController = new NavxController("HeadingController", RobotMap.navP, RobotMap.navI, RobotMap.navD,
        		RobotMap.navF, RobotMap.navx);
        
        // Drive system
		robotDrive = new CustomRobotDrive(RobotMap.dtLeft, RobotMap.dtRight, RobotMap.dtLeftController, RobotMap.dtRightController, navController);
	    LiveWindow.addActuator("LeftRobotDrive", "Controller", RobotMap.dtLeftController);
	    LiveWindow.addActuator("RightRobotDrive", "Controller", RobotMap.dtRightController);	    
		
		driveTrain = new DriveTrain(robotDrive);
		
		// Put all the systems onto smart dashboard
		
    	//Init OI last so all systems initialized
		oi = new OI();
		
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(navController);
		updateSmartDashboard();
    }

    public void updateSmartDashboard() {
        driveTrain.updateSmartDashboard();
        //shooter.updateSmartDashboard();
        navController.updateSmartDashboard();	
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
        updateSmartDashboard();
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
        updateSmartDashboard();
    }

    /**
     * Loops during test.
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
