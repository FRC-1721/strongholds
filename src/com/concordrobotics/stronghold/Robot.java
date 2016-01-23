
package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.subsystems.ExampleSubsystem;

//import brennan.brennan.robotlogger.RobotLogger;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
//	final int gyroChan = 0;
//	final int joystickChan = 1;
	
//	double angleSetPoint = 0.;
//	final double pGain = .024;
//	final double iGain = .04;
//	final double dGain = 0;
//	final double fGain = .1;
	
	
//	final double voltsPerDegreePerSecond = .007;
//	public static RobotLogger log = new RobotLogger();
	

    Command autonomousCommand;
//    SendableChooser chooser;
//    PIDController gyroControl, gyroControl1;
//    CustomPIDController leftControl, rightControl;
    RobotMap rm;
    Joystick stick1, stick2;
//    AnalogGyro gyro;
//    CustomPIDOutput gyroOut;
    
//    CustomEncoder leftEncoder;
//    CustomEncoder rightEncoder;
    
    /***************
     * DRIVE TRAIN *
     ***************/
    Victor dtvLeft, dtvRight,shootLeft, shootRight, kicker, shootUpDown;
    RobotDrive drive;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public void robotInit() {
//		gyro = new AnalogGyro(gyroChan);
//    	gyro.setSensitivity(voltsPerDegreePerSecond); //calibrates gyro values to equal degrees
//    	gyro.setPIDSourceType(PIDSourceType.kRate);
//    	gyroOut = new CustomPIDOutput();
    	oi = new OI();
    	stick1 = new Joystick(1);
    	stick2 = new Joystick(2);
    	//LiveWindow.setEnabled(true);
    	SmartDashboard.putData(Scheduler.getInstance());
//    	SmartDashboard.putData("gyro", gyro);
    	dtvLeft = new Victor(1);
		dtvRight = new Victor(2);
//		shootLeft = new Victor(3);
//		shootRight = new Victor(4);
//		kicker = new Victor(5);
//		shootUpDown = new Victor(6);
//    	dtvRight.setInverted(true);
    	drive = new RobotDrive(dtvRight, dtvLeft);
//    	leftEncoder = new CustomEncoder(0, 1);
//    	rightEncoder = new CustomEncoder(2, 3);
    	//gyroControl = new PIDController(pGain, iGain, dGain, gyro, gyroOut);
    	//gyroControl1 = new PIDController(pGain, iGain, dGain, gyro, dtvRight);
//    	leftControl = new CustomPIDController(pGain, iGain, dGain, fGain, leftEncoder, dtvLeft);
//    	rightControl = new CustomPIDController(pGain, iGain, dGain, fGain, rightEncoder, dtvRight);
//    	leftEncoder.setPIDSourceType(PIDSourceType.kRate);
//    	rightEncoder.setPIDSourceType(PIDSourceType.kRate);
//    	leftControl.setPercentTolerance(1);
//    	rightControl.setPercentTolerance(1);
//    	leftEncoder.setDistancePerPulse(.013);
//    	rightEncoder.setDistancePerPulse(.0095);
//    	LiveWindow.addSensor("Left Encoder", "LEFT", leftEncoder);
//    	LiveWindow.addSensor("Right Encoder", "RIGHT", rightEncoder);
//    	LiveWindow.addActuator("Left CONTROL", "LEFT", leftControl);
//    	LiveWindow.addActuator("Right CONTROL", "RIGHT", rightControl);
//    	leftEncoder.reset();
//    	rightEncoder.reset();
    	//gyroControl.setAbsoluteTolerance(5.0);
    	//gyroControl1.setAbsoluteTolerance(5.0);
    	//LiveWindow.addActuator("Controller 1", "test", gyroControl);
    	//LiveWindow.addActuator("Controller 2", "Whatever", gyroControl1);
//		log.init();
//		log.log("Robot Code Initialized...", "BOOT");
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
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

	public void teleopInit() {
//    	log.log("Teleop has been initialized. Using VICTORS on ports: " + rm.dtRight + ", " + rm.dtLeft, "INFO");
//		gyro.reset();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        //double angle = gyro.getAngle();
        
        while (isOperatorControl() && isEnabled()) {
        	drive.tankDrive(stick1, stick2);
        	Timer.delay(0.01);
        }
        //double turningValue;
/*
    	while (isOperatorControl() && isEnabled()) {
            
            turningValue =  stick1;
            if(stick1.getY() <= 0)
            {
        	//forwards
        	drive.drive(stick1.getY(), -turningValue); 
            } else {
        	//backwards
        	drive.drive(stick1.getY(), turningValue); 
            }
        }
    	Timer.delay(0.01);
    	SmartDashboard.putNumber("Heading", gyro.getAngle());
    	SmartDashboard.putData("gyro", gyro);
    */} 
    /**
     * This function is called periodically during test mode
     */
    public void testInit() {
    	
    }
    public void testPeriodic() {
//    	gyroControl.calculate();
        LiveWindow.run();
        
    }
}
