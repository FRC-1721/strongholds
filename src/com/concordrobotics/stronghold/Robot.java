
package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.subsystems.ExampleSubsystem;

import edu.wpi.first.wpilibj.AnalogGyro;
//import brennan.brennan.robotlogger.RobotLogger;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	
	final int gyroChan = 0;
	final int joystickChan = 1;
	
	double angleSetPoint = 0.0;
	final double pGain = .024;
	
	final double voltsPerDegreePerSecond = .0128;
//	public static RobotLogger log = new RobotLogger();
	

    Command autonomousCommand;
    SendableChooser chooser;
    RobotMap rm;
    Joystick stick1;
    AnalogGyro gyro;
    /***************
     * DRIVE TRAIN *
     ***************/
    Victor dtvLeft, dtvRight;
    RobotDrive drive;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @SuppressWarnings("static-access")
	public void robotInit() {
		gyro = new AnalogGyro(gyroChan);
    	oi = new OI();
//		log.init();
//		log.log("Robot Code Initialized...", "BOOT");
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    @SuppressWarnings("static-access")
	public void disabledInit(){
    	//log.log("Robot has been disabled!", "INFO");
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {}

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {}

    @SuppressWarnings("static-access")
	public void teleopInit() {
    	
    	dtvRight = new Victor(rm.dtRight);
    	dtvLeft = new Victor(rm.dtLeft);
    	drive = new RobotDrive(dtvRight, dtvLeft);
//    	log.log("Teleop has been initialized. Using VICTORS on ports: " + rm.dtRight + ", " + rm.dtLeft, "INFO");
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        /*Scheduler.getInstance().run();
        while (isOperatorControl() && isEnabled()) {
        	drive.arcadeDrive(stick1);
        	Timer.delay(0.01);
        }*/
    	double turningValue;
    	gyro.setSensitivity(voltsPerDegreePerSecond); //calibrates gyro values to equal degrees
        while (isOperatorControl() && isEnabled()) {
            
            turningValue =  (angleSetPoint - gyro.getAngle())*pGain;
            if(stick1.getY() <= 0)
            {
        	//forwards
        	drive.drive(stick1.getY(), turningValue); 
            } else {
        	//backwards
        	drive.drive(stick1.getY(), -turningValue); 
            }
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
