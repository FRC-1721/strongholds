
package com.concordrobotics.stonghold;

import com.concordrobotics.stronghold.commands.DriveCommand;
import com.concordrobotics.stronghold.subsystems.DriveTrain;
import com.concordrobotics.stronghold.subsystems.Shooter;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
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

	public static OI oi;
	public static Shooter shooter;
	public static DriveTrain driveTrain;
	Command driveCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	// Init subsystems
		shooter = new Shooter(RobotMap.shooterP, RobotMap.shooterI, RobotMap.shooterD);
		driveTrain = new DriveTrain();
		
		// Init motors
		RobotMap.shootA = new VictorSP(RobotMap.spShootA);
		RobotMap.shootL = new VictorSP(RobotMap.spShootL);
		RobotMap.shootR = new VictorSP(RobotMap.spShootR);
		RobotMap.dtLeft = new VictorSP(RobotMap.spLeftP);
		RobotMap.dtRight = new VictorSP(RobotMap.spRightP);
		RobotMap.shootK = new Servo(RobotMap.kickerP);
		
		// REVERSE DRIVE/PITCH MOTORS
		RobotMap.dtLeft.setInverted(true);
		RobotMap.dtRight.setInverted(true);
		RobotMap.shootA.setInverted(true);
		
		// Init drive
		RobotMap.drive = new RobotDrive(RobotMap.dtLeft, RobotMap.dtRight);
		
		// Init Robot Vision
		RobotMap.camera = CameraServer.getInstance();
		RobotMap.camera.setQuality(50);
		RobotMap.camera.startAutomaticCapture(); // Start the video
		
		// Init encoder
		RobotMap.shootEnc = new Encoder(RobotMap.encShootA, RobotMap.encShootB);
		RobotMap.shootEnc.setDistancePerPulse(0.1052);
		
		// Init OI
		oi = new OI();
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

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
    public void autonomousInit() {
    	
    
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	driveCommand = new DriveCommand();
    	
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Shoot Encoder", RobotMap.shootEnc.getDistance());
//        RobotMap.driveTrain.jInput(OI.jLeft, OI.jRight);
        Timer.delay(0.01);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	RobotMap.shootK.setAngle(20);
        LiveWindow.run();
    }
}
