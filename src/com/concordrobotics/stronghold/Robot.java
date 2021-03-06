package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.commands.AutoCrossMoat;
import com.concordrobotics.stronghold.commands.AutoCrossTeeterTotter;
import com.concordrobotics.stronghold.commands.AutoLowBar;
import com.concordrobotics.stronghold.commands.AutoNone;
import com.concordrobotics.stronghold.commands.CustomCommandGroup;
import com.concordrobotics.stronghold.subsystems.DistanceDrivePID;
import com.concordrobotics.stronghold.subsystems.DriveTrain;
import com.concordrobotics.stronghold.subsystems.DriveTrain.DriveMode;
import com.concordrobotics.stronghold.subsystems.DriveTrain.GyroMode;
import com.concordrobotics.stronghold.subsystems.NavxController;
import com.concordrobotics.stronghold.subsystems.Shooter;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
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
import edu.wpi.first.wpilibj.command.CommandGroup;
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
    CommandGroup autonomousCommand;
    SendableChooser autoChooser;
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
		RobotMap.dtRightEnc.setDistancePerPulse(0.0065);
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
		
		RobotMap.backPing = new CustomUltrasonic(0);
		RobotMap.leftPing = new CustomUltrasonic(1);
	
		
		//RobotMap.pot = new AnalogPotentiometer(0,312.5,0);
		
		//RobotMap.wire = new I2C(I2C.Port.kOnboard, 4);
		
        preferences =Preferences.getInstance();
        getPreferences();
        setPreferences();
        
		// Create a chooser for auto so it can be set from the DS
		autonomousCommand = new AutoCrossMoat();
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Low Bar", new AutoLowBar());
		autoChooser.addObject("Cross Terrain", new AutoCrossMoat());
		autoChooser.addObject("TeeterTotter", new AutoCrossTeeterTotter());
		autoChooser.addObject("None", new AutoNone());
		SmartDashboard.putData("Auto Chooser", autoChooser);
		
		
		
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
        SmartDashboard.putNumber("leftPing", RobotMap.leftPing.getDistance());
        SmartDashboard.putNumber("backPing", RobotMap.backPing.getDistance());
        positionEstimator.updateSmartDashboard();
       // SmartDashboard.putNumber("Ultrasonic_DistanceIn", RobotMap.ultrasonic.getRangeInches() );
        
      //  SmartDashboard.putNumber("Pots Value", RobotMap.pot.get());
        
        allEndOfPeriodic();
    }
    
    /**
     * Triggered when the robot is disabled (every time).
     */
    public void disabledInit(){
    	// This should trigger technodubstepmode when the robot is disabled
    	// I.E. End of a match
    //	LEDController.sendLED(RobotMap.patWait);
    }
    
    /**
     * Loops while the robot is disabled.
     */
	public void disabledPeriodic() {
		//getPreferences();	
		switch (RobotMap.alliance) {
			case Red:
				if (!(currentLEDMode == 1)) {
					// Throw a RED ALLIANCE packet via I2C
					//LEDController.sendLED(RobotMap.patRed);
					currentLEDMode = 1;
				}
				break;
			case Blue:
				if (!(currentLEDMode == 2)) {
					// Throw a BLUE ALLIANCE packet via I2C
					//LEDController.sendLED(RobotMap.patBlue);
					currentLEDMode = 2;
				}
				break;
			case Invalid:
				// DO NOTHING
				break;
			default:
				// Won't ever happen.... I hope
				break;
		}
		Scheduler.getInstance().run();
	}

	public static double getStationX(int station) {
		return ((double) station - 0.5)*(4.0 + 2.0/12.0) - 0.5*(26.0 + 7.0/12.0);
	}
	/**
	 * Triggers when auto starts.
	 */
    public void autonomousInit() {
    	allInit(RobotMode.AUTONOMOUS);
    	// Reference line is y = 0
    	// Tower is at x = 0
    	RobotMap.yStart = getStationX( RobotMap.autoStartStation );
    	RobotMap.xStart = 2.0;
    	positionEstimator.setPosition(RobotMap.xStart, RobotMap.yStart);
    	autonomousCommand = (CommandGroup) autoChooser.getSelected();
    	//autonomousCommand.addCommands();
    	autonomousCommand.start();
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
    	if (RobotMap.teleopUseGyro) {
    		Robot.driveTrain.setGyroMode(DriveTrain.GyroMode.rate);
    	} else {
    		Robot.driveTrain.setGyroMode(DriveTrain.GyroMode.off);
    	}
    	allInit(RobotMode.TELEOP);
    	/*if (!(currentLEDMode == 3)) {
    		LEDController.sendLED(RobotMap.patNone);
    		currentLEDMode = 3;
    	}*/
    	
    }

    public void testInit() {
    	allInit(RobotMode.TEST);
    	if (!(currentLEDMode == 4)) {
    		//LEDController.sendLED(RobotMap.patTest);
    		currentLEDMode = 4;
    	}
    }
    /**
     * Loops during teleop.
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        updateSmartDashboard();
       // LEDController.sendLED(RobotMap.patTest);
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
		RobotMap.shooterUsePot = preferences.getBoolean(PreferencesNames.SHOOTER_USE_POT, RobotMap.shooterUsePot);
		
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
		
		RobotMap.autoStartStation = preferences.getInt(PreferencesNames.AUTONOMOUS_START_STATION, RobotMap.autoStartStation);
		RobotMap.autoDriveAngle = preferences.getDouble(PreferencesNames.AUTONOMOUS_DRIVE_ANGLE, RobotMap.autoDriveAngle);
		RobotMap.autoDriveAngle2 = preferences.getDouble(PreferencesNames.AUTONOMOUS_DRIVE_ANGLE2, RobotMap.autoDriveAngle2);
		RobotMap.autoDriveDistance = preferences.getDouble(PreferencesNames.AUTONOMOUS_DRIVE_DISTANCE, RobotMap.autoDriveDistance);
		
		RobotMap.autoShootAngle = preferences.getDouble(PreferencesNames.AUTONOMOUS_SHOOTER_ANGLE, RobotMap.autoShootAngle);
		RobotMap.xAutoShootPosition = preferences.getDouble(PreferencesNames.AUTONOMOUS_SHOOT_X, RobotMap.xAutoShootPosition);
		RobotMap.yAutoShootPosition = preferences.getDouble(PreferencesNames.AUTONOMOUS_SHOOT_Y, RobotMap.yAutoShootPosition);
		
		RobotMap.teleopArcadeDrive = preferences.getBoolean(PreferencesNames.TELEOP_ARCADE_DRIVE, RobotMap.teleopArcadeDrive);
		if (RobotMap.teleopArcadeDrive) {
			RobotMap.teleopDriveMode = DriveMode.arcadeMode;
		} else {
			RobotMap.teleopDriveMode = DriveMode.tankMode;
		}
		RobotMap.redAlliance = preferences.getBoolean(PreferencesNames.RED_ALLIANCE, RobotMap.redAlliance);
		if (RobotMap.redAlliance) {
			RobotMap.alliance = Alliance.Red;
		} else {
			RobotMap.alliance = Alliance.Blue;
		}
		RobotMap.teleopUseGyro = preferences.getBoolean(PreferencesNames.TELEOP_USE_GYRO, RobotMap.teleopUseGyro);
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
		preferences.putBoolean(PreferencesNames.SHOOTER_USE_POT, RobotMap.shooterUsePot);
		preferences.putBoolean(PreferencesNames.TELEOP_USE_GYRO, RobotMap.teleopUseGyro);
		
		preferences.putDouble(PreferencesNames.DRIVE_PID_P, RobotMap.dtP);
		preferences.putDouble(PreferencesNames.DRIVE_PID_D, RobotMap.dtD);
		preferences.putDouble(PreferencesNames.DRIVE_PID_F, RobotMap.dtF);

		preferences.putDouble(PreferencesNames.DIST_PID_P, RobotMap.distP);
		preferences.putDouble(PreferencesNames.DIST_PID_I, RobotMap.distI);
		preferences.putDouble(PreferencesNames.DIST_PID_D, RobotMap.distD);
		preferences.putInt(PreferencesNames.AUTONOMOUS_START_STATION, RobotMap.autoStartStation);
		
		preferences.putInt(PreferencesNames.AUTONOMOUS_START_STATION, RobotMap.autoStartStation);
		preferences.putDouble(PreferencesNames.AUTONOMOUS_SHOOTER_ANGLE, RobotMap.autoShootAngle);
		preferences.putDouble(PreferencesNames.AUTONOMOUS_SHOOT_X, RobotMap.xAutoShootPosition);
		preferences.putDouble(PreferencesNames.AUTONOMOUS_SHOOT_Y, RobotMap.yAutoShootPosition);
		
		preferences.putDouble(PreferencesNames.AUTONOMOUS_DRIVE_ANGLE, RobotMap.autoDriveAngle);
		preferences.putDouble(PreferencesNames.AUTONOMOUS_DRIVE_ANGLE2, RobotMap.autoDriveAngle2);
		preferences.putDouble(PreferencesNames.AUTONOMOUS_DRIVE_DISTANCE, RobotMap.autoDriveDistance);
	}
	
	public static void dumpPreferences()
	{
		for(Object a: preferences.getKeys())
		{
			System.out.println(a + "=" + preferences.getString((String) a, "foobar"));
		}
	}
}
