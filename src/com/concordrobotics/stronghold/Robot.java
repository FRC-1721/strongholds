
package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.commands.AutoCrossMoat;
import com.concordrobotics.stronghold.commands.AutoCrossTeeterTotter;
import com.concordrobotics.stronghold.commands.AutoLowBar;
import com.concordrobotics.stronghold.commands.AutoNone;
import com.concordrobotics.stronghold.subsystems.DistanceDrivePID;
import com.concordrobotics.stronghold.subsystems.DriveTrain;
import com.concordrobotics.stronghold.subsystems.DriveTrain.GyroMode;
import com.concordrobotics.stronghold.subsystems.NavxController;
import com.concordrobotics.stronghold.subsystems.Shooter;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Concord Robotics FRC Team 1721
 * 2016 - FIRST STRONGHOLD
 * Robot.java
 *
 * This is the main class for the entire robot.
 * The VM is set to run this class before anything else.
 */
public class Robot extends IterativeRobot {
	// Autonomous commad
    Command autonomousCommand;
    SendableChooser autoChooser;
    SendableChooser positionChooser;
	// Subsystems
	public static DriveTrain driveTrain;
	public static CustomRobotDrive robotDrive;
	public static Shooter shooter;
	public static OI oi;
	public static NavxController navController;
	public static DistanceDrivePID distanceDrivePID;
    private final double kMetersToFeet = 3.28084;
    DriverStation ds;
	public static PositionEstimator positionEstimator;
	public static PositionSetter positionSetter ;
	public static Preferences preferences;
	static private RobotMode currentRobotMode = RobotMode.INIT, previousRobotMode;
	PowerDistributionPanel pdp;
	
	DriverStation driverStation = DriverStation.getInstance();
	
	private int currentLEDMode = 0;

    public void robotInit() {
		//Init motor and controllers
		RobotMap.dtLeft = new VictorSP(RobotMap.spLeftPort);
		RobotMap.dtRight = new VictorSP(RobotMap.spRightPort);
		RobotMap.dtRight.setInverted(true);
		LiveWindow.addActuator("LeftRobotDrive", "Victor", RobotMap.dtLeft);
		LiveWindow.addActuator("RightRobotDrive", "Victor", RobotMap.dtRight);
		RobotMap.dtLeftEnc = new Encoder(RobotMap.dtLeftEncPortA, RobotMap.dtLeftEncPortB, RobotMap.dtLeftEncReversed);	  
		RobotMap.dtRightEnc = new Encoder(RobotMap.dtRightEncPortA, RobotMap.dtRightEncPortB, RobotMap.dtRightEncReversed);
		RobotMap.dtLeftEnc.setDistancePerPulse(0.00931);
		RobotMap.dtRightEnc.setDistancePerPulse(0.0134);
		LiveWindow.addSensor("LeftRobotDrive", "Encoder", RobotMap.dtLeftEnc);
	    LiveWindow.addSensor("RightRobotDrive", "Encoder", RobotMap.dtRightEnc);
	    RobotMap.dtLeftController = new CustomPIDController(RobotMap.dtP, RobotMap.dtI, RobotMap.dtD, RobotMap.dtF,
	    												RobotMap.dtLeftEnc, RobotMap.dtLeft, 0.03);
	    RobotMap.dtRightController = new CustomPIDController(RobotMap.dtP, RobotMap.dtI, RobotMap.dtD, RobotMap.dtF,
				RobotMap.dtRightEnc, RobotMap.dtRight, 0.03);
	  //  RobotMap.velDriveController = new VelocityDrivePID(RobotMap.dtP, RobotMap.dtI, RobotMap.dtD, RobotMap.dtF);
	    
		//Shooter 
	    shooter = new Shooter(RobotMap.shooterP, RobotMap.shooterI, RobotMap.shooterD);
		RobotMap.shootA = new VictorSP(RobotMap.spShootA);
		RobotMap.shootL = new VictorSP(RobotMap.spShootL);
		RobotMap.shootR = new VictorSP(RobotMap.spShootR);
		RobotMap.shootK = new Servo(RobotMap.kickerP);
		RobotMap.shootA.setInverted(true);
		// Init encoder
		RobotMap.shootEnc = new Encoder(RobotMap.encShootA, RobotMap.encShootB);
		RobotMap.shootEnc.setDistancePerPulse(0.1052);
		
		// Gyro and controller
        RobotMap.navx = new AHRS(SPI.Port.kMXP, RobotMap.kNavUpdateHz); 
        LiveWindow.addSensor("Gyro", "navx", RobotMap.navx);
        navController = new NavxController("HeadingController", RobotMap.navP, RobotMap.navI, RobotMap.navD,
        		RobotMap.navF, RobotMap.navx, PIDSourceType.kDisplacement);
        positionEstimator = new PositionEstimator();
        
        // Drive system
		robotDrive = new CustomRobotDrive(RobotMap.dtLeft, RobotMap.dtRight, RobotMap.dtLeftController, RobotMap.dtRightController, navController);
	    LiveWindow.addActuator("LeftRobotDrive", "Controller", RobotMap.dtLeftController);
	    LiveWindow.addActuator("RightRobotDrive", "Controller", RobotMap.dtRightController);	    
		
		driveTrain = new DriveTrain(robotDrive);
		distanceDrivePID = new DistanceDrivePID(RobotMap.distP, RobotMap.distI, RobotMap.distD);
		distanceDrivePID.disable();

		// Init Robot Vision
		RobotMap.camera = CameraServer.getInstance();
		RobotMap.camera.setQuality(50);
		RobotMap.camera.startAutomaticCapture(); // Start the video
		
		RobotMap.ultrasonic = new Ultrasonic(RobotMap.uOut, RobotMap.uIn);
		RobotMap.ultrasonic.setAutomaticMode(true);
		RobotMap.ultrasonic.setEnabled(true);
		
		RobotMap.pot = new AnalogPotentiometer(0,312.5,0);
		
		RobotMap.wire = new I2C(I2C.Port.kOnboard, 4);
		
		
		// Create a chooser for auto so it can be set from the DS
		autonomousCommand = new AutoCrossMoat();
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Low Bar", new AutoLowBar());
		autoChooser.addObject("Cross Terrain", new AutoCrossMoat());
		autoChooser.addObject("TeeterTotter", new AutoCrossTeeterTotter());
		autoChooser.addObject("None", new AutoNone());
		SmartDashboard.putData("Auto Chooser", autoChooser);
		
		// Create a chooser for field position
		positionChooser = new SendableChooser();
		positionChooser.addDefault("1", new PositionSetter(1));
		positionChooser.addObject("2", new PositionSetter(2));
		positionChooser.addObject("3", new PositionSetter(3));
		positionChooser.addObject("4", new PositionSetter(4));	
		positionChooser.addObject("5", new PositionSetter(5));
		
		
    	//Init OI last so all systems initialized
		oi = new OI();
		// Put all the systems onto smart dashboard
		
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(navController);
		updateSmartDashboard();

		
    }

    public void updateSmartDashboard() {
        //driveTrain.updateSmartDashboard();
        shooter.updateSmartDashboard();
        navController.updateSmartDashboard();	
        //distanceDrivePID.updateSmartDashboard();
        //navxUpdateSmartDashboard();
        SmartDashboard.putNumber("Left_Encoder_Distance", RobotMap.dtLeftEnc.getDistance());
        SmartDashboard.putNumber("Left_Encoder_Rate", RobotMap.dtLeftEnc.getRate());
        
        
        SmartDashboard.putNumber("Right_Encoder_Distance", RobotMap.dtRightEnc.getDistance());
        SmartDashboard.putNumber("Right_Encoder_Rate", RobotMap.dtRightEnc.getRate());

        positionEstimator.updateSmartDashboard();
        SmartDashboard.putNumber("Ultrasonic_DistanceIn", RobotMap.ultrasonic.getRangeInches() );
        
        SmartDashboard.putNumber("Pots Value", RobotMap.pot.get());
        
        allEndOfPeriodic();
    }
    
    /**
     * Triggered when the robot is disabled (every time).
     */
    public void disabledInit(){
    	
    	LEDController.sendLED(RobotMap.patWait);
    }
    
    /**
     * Loops while the robot is disabled.
     */
	public void disabledPeriodic() {
		RobotMap.alliance = ds.getAlliance();
			
		switch (RobotMap.alliance) {
			case Red:
				if (!(currentLEDMode == 1)) {
					// Throw a RED ALLIANCE packet via I2C
					LEDController.sendLED(RobotMap.patRed);
					currentLEDMode = 1;
				}
				break;
			case Blue:
				if (!(currentLEDMode == 2)) {
					LEDController.sendLED(RobotMap.patBlue);
					currentLEDMode = 2;
				}
				break;
			case Invalid:
				// DO NOTHING
				break;
			default:
				// Won't ever happen
				break;
		}
		Scheduler.getInstance().run();
	}

	/**
	 * Triggers when auto starts.
	 */
    public void autonomousInit() {
    	allInit(RobotMode.AUTONOMOUS);
    	autonomousCommand = (Command) autoChooser.getSelected();
    	/*positionSetter = (PositionSetter) positionChooser.getSelected();
    	positionSetter.set(); */
    	autonomousCommand.start();
    	
    	switch(RobotMap.alliance) {
    		case Invalid:
    			// No alliance was chosen, and auto is starting.
    			// Just default to a nice color
    			LEDController.sendLED(RobotMap.patNone);
    			currentLEDMode = 3;
    			break;
    		default:
    			break;
    	}
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
    	autonomousCommand.cancel();
    	Robot.robotDrive.disablePID();
    	Robot.driveTrain.setGyroMode(DriveTrain.GyroMode.off);
    	allInit(RobotMode.TELEOP);
    	
    	switch(RobotMap.alliance) {
    		case Invalid:
    			// Just in case auto was kill.
    			// We'll give it safety
    			if (!(currentLEDMode == 3)) {
    				LEDController.sendLED(RobotMap.patNone);
    				currentLEDMode = 3;
    			}
    			break;
    		default:
    			break;
    	}
    	
    }

    public void testInit() {
    	allInit(RobotMode.TEST);
    	if (!(currentLEDMode == 4)) {
    		LEDController.sendLED(RobotMap.patTest);
    		currentLEDMode = 4;
    	}
    }
    /**
     * Loops during teleop.
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        updateSmartDashboard();
        LEDController.sendLED(RobotMap.patTest);
    }

    /**
     * Loops during test.
     */
    public void testPeriodic() {
    	RobotMap.shootK.setAngle(5);
        LiveWindow.run();
        allEndOfPeriodic();
    }
    
    public void navxUpdateSmartDashboard() {
    	 /* Display 6-axis Processed Angle Data                                      */
        SmartDashboard.putBoolean(  "IMU_Connected",        RobotMap.navx.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    RobotMap.navx.isCalibrating());
        SmartDashboard.putNumber(   "IMU_Yaw",              RobotMap.navx.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            RobotMap.navx.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             RobotMap.navx.getRoll());
        
        /* Display tilt-corrected, Magnetometer-based heading (requires             */
        /* magnetometer calibration to be useful)                                   */
        
        SmartDashboard.putNumber(   "IMU_CompassHeading",   RobotMap.navx.getCompassHeading());
        
        /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
        SmartDashboard.putNumber(   "IMU_FusedHeading",     RobotMap.navx.getFusedHeading());

        /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
        /* path for upgrading from the Kit-of-Parts gyro to the navx-MXP            */
        
        SmartDashboard.putNumber(   "IMU_TotalYaw",         RobotMap.navx.getAngle());
        SmartDashboard.putNumber(   "IMU_YawRateDPS",       RobotMap.navx.getRate());

        /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
        
        SmartDashboard.putNumber(   "IMU_Accel_X",          RobotMap.navx.getWorldLinearAccelX()*kMetersToFeet);
        SmartDashboard.putNumber(   "IMU_Accel_Y",          RobotMap.navx.getWorldLinearAccelY()*kMetersToFeet);
        SmartDashboard.putBoolean(  "IMU_IsMoving",         RobotMap.navx.isMoving());
        SmartDashboard.putBoolean(  "IMU_IsRotating",       RobotMap.navx.isRotating());

        /* Display estimates of velocity/displacement.  Note that these values are  */
        /* not expected to be accurate enough for estimating robot position on a    */
        /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
        /* of these errors due to single (velocity) integration and especially      */
        /* double (displacement) integration.                                       */
        
        SmartDashboard.putNumber(   "Velocity_X",           RobotMap.navx.getVelocityX()*kMetersToFeet);
        SmartDashboard.putNumber(   "Velocity_Y",           RobotMap.navx.getVelocityY()*kMetersToFeet);
        SmartDashboard.putNumber(   "Displacement_X",       RobotMap.navx.getDisplacementX()*kMetersToFeet);
        SmartDashboard.putNumber(   "Displacement_Y",       RobotMap.navx.getDisplacementY()*kMetersToFeet);
        
        /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
        /* NOTE:  These values are not normally necessary, but are made available   */
        /* for advanced users.  Before using this data, please consider whether     */
        /* the processed data (see above) will suit your needs.                     */
        
        SmartDashboard.putNumber(   "RawGyro_X",            RobotMap.navx.getRawGyroX()*kMetersToFeet);
        SmartDashboard.putNumber(   "RawGyro_Y",            RobotMap.navx.getRawGyroY()*kMetersToFeet);
        SmartDashboard.putNumber(   "RawGyro_Z",            RobotMap.navx.getRawGyroZ()*kMetersToFeet);
        SmartDashboard.putNumber(   "RawAccel_X",           RobotMap.navx.getRawAccelX()*kMetersToFeet);
        SmartDashboard.putNumber(   "RawAccel_Y",           RobotMap.navx.getRawAccelY()*kMetersToFeet);
        SmartDashboard.putNumber(   "RawAccel_Z",           RobotMap.navx.getRawAccelZ()*kMetersToFeet);
        SmartDashboard.putNumber(   "RawMag_X",             RobotMap.navx.getRawMagX()*kMetersToFeet);
        SmartDashboard.putNumber(   "RawMag_Y",             RobotMap.navx.getRawMagY()*kMetersToFeet);
        SmartDashboard.putNumber(   "RawMag_Z",             RobotMap.navx.getRawMagZ()*kMetersToFeet);
        SmartDashboard.putNumber(   "IMU_Temp_C",           RobotMap.navx.getTempC());
        
        /* Sensor Board Information                                                 */
        SmartDashboard.putString(   "FirmwareVersion",      RobotMap.navx.getFirmwareVersion());
        
        /* Quaternion Data                                                          */
        /* Quaternions are fascinating, and are the most compact representation of  */
        /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
        /* from the Quaternions.  If interested in motion processing, knowledge of  */
        /* Quaternions is highly recommended.   
                                           
        SmartDashboard.putNumber(   "QuaternionW",          RobotMap.navx.getQuaternionW());
        SmartDashboard.putNumber(   "QuaternionX",          RobotMap.navx.getQuaternionX());
        SmartDashboard.putNumber(   "QuaternionY",          RobotMap.navx.getQuaternionY());
        SmartDashboard.putNumber(   "QuaternionZ",          RobotMap.navx.getQuaternionZ());
         */
        
        /* Connectivity Debugging Support                                           */
        // SmartDashboard.putNumber(   "IMU_Byte_Count",       RobotMap.navx.getByteCount());
        // SmartDashboard.putNumber(   "IMU_Update_Count",     RobotMap.navx.getUpdateCount());
    }

    /**
	 * This gets called at the end of any *periodic().
	 * We're putting this here "just in case".
	 */
	void allEndOfPeriodic(){
		
	}
    
	public static RobotMode getCurrentRobotMode() {
		return currentRobotMode;
	}
	
	public static RobotMode getPreviousRobotMode() {
		return previousRobotMode;
	}
	
	public static void commandInitialized(Object o) {
	}

	public static void commandEnded(Object o) {
	}

	public static void commandInterrupted(Object o) {
	}
	
	void allInit (RobotMode newMode)
	{
		previousRobotMode = currentRobotMode;
		currentRobotMode = newMode;
		getPreferences();
		setPreferences();
		if (RobotMap.loggingEnabled) {
			
		}
		dumpPreferences();
		// if anyone needs to know about mode changes, let
		// them know here.

	}
	
	
	public static void getPreferences() {
		RobotMap.navP = preferences.getDouble(PreferencesNames.GYRO_HEADING_P, RobotMap.navP);
		RobotMap.navI = preferences.getDouble(PreferencesNames.GYRO_HEADING_I, RobotMap.navI);
		RobotMap.navD = preferences.getDouble(PreferencesNames.GYRO_HEADING_D, RobotMap.navD);
		RobotMap.navRateP = preferences.getDouble(PreferencesNames.GYRO_RATE_P, RobotMap.navRateP);
		RobotMap.navRateD = preferences.getDouble(PreferencesNames.GYRO_RATE_D, RobotMap.navRateD);		
		RobotMap.navRateF = preferences.getDouble(PreferencesNames.GYRO_RATE_F, RobotMap.navRateF);
		// Set the gyro mode to refresh PID parameters
		GyroMode gMode = Robot.driveTrain.getGyroMode();
		Robot.driveTrain.setGyroMode(gMode);
		
		RobotMap.useDrivePIDinAuto = preferences.getBoolean(PreferencesNames.AUTONOMOUS_USE_DRIVE_PID, RobotMap.useDrivePIDinAuto);
		RobotMap.leftEncoderDisabled = preferences.getBoolean(PreferencesNames.LEFT_DRIVE_ENCODER_DISABLED, RobotMap.leftEncoderDisabled);
		RobotMap.rightEncoderDisabled = preferences.getBoolean(PreferencesNames.RIGHT_DRIVE_ENCODER_DISABLED, RobotMap.rightEncoderDisabled);
		
		RobotMap.dtP = preferences.getDouble(PreferencesNames.DRIVE_PID_P, RobotMap.dtP);
		RobotMap.dtD = preferences.getDouble(PreferencesNames.DRIVE_PID_D, RobotMap.dtD);
		RobotMap.dtF = preferences.getDouble(PreferencesNames.DRIVE_PID_F, RobotMap.dtF);
		RobotMap.dtLeftController.setPID(RobotMap.dtP, RobotMap.dtI, RobotMap.dtD, RobotMap.dtF);
		RobotMap.dtRightController.setPID(RobotMap.dtP, RobotMap.dtI, RobotMap.dtD, RobotMap.dtF);
		
		RobotMap.distP = preferences.getDouble(PreferencesNames.DIST_PID_P, RobotMap.distP);
		RobotMap.distI = preferences.getDouble(PreferencesNames.DIST_PID_I, RobotMap.distI);
		RobotMap.distD = preferences.getDouble(PreferencesNames.DIST_PID_D, RobotMap.distD);
		CustomPIDController ddController = Robot.distanceDrivePID.getPIDController();
		ddController.setPID(RobotMap.distP, RobotMap.distI, RobotMap.distD);
		
	}

	public static void setPreferences() {
		preferences.putDouble(PreferencesNames.GYRO_HEADING_P, RobotMap.navP);
		preferences.putDouble(PreferencesNames.GYRO_HEADING_I, RobotMap.navI);
	    preferences.putDouble(PreferencesNames.GYRO_HEADING_D, RobotMap.navD);
		preferences.putDouble(PreferencesNames.GYRO_RATE_P, RobotMap.navRateP);
		preferences.putDouble(PreferencesNames.GYRO_RATE_D, RobotMap.navRateD);		
		preferences.putDouble(PreferencesNames.GYRO_RATE_F, RobotMap.navRateF);

		
		preferences.putBoolean(PreferencesNames.AUTONOMOUS_USE_DRIVE_PID, RobotMap.useDrivePIDinAuto);
		preferences.putBoolean(PreferencesNames.LEFT_DRIVE_ENCODER_DISABLED, RobotMap.leftEncoderDisabled);
		preferences.putBoolean(PreferencesNames.RIGHT_DRIVE_ENCODER_DISABLED, RobotMap.rightEncoderDisabled);
		
		preferences.putDouble(PreferencesNames.DRIVE_PID_P, RobotMap.dtP);
		preferences.putDouble(PreferencesNames.DRIVE_PID_D, RobotMap.dtD);
		preferences.putDouble(PreferencesNames.DRIVE_PID_F, RobotMap.dtF);

		preferences.putDouble(PreferencesNames.DIST_PID_P, RobotMap.distP);
		preferences.putDouble(PreferencesNames.DIST_PID_I, RobotMap.distI);
		preferences.putDouble(PreferencesNames.DIST_PID_D, RobotMap.distD);
		
	}
	
	public static void dumpPreferences()
	{
		for(Object a: preferences.getKeys())
		{
			System.out.println(a + "=" + preferences.getString((String) a, "foobar"));
		}
	}
}
