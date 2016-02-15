package com.concordrobotics.stronghold.subsystems;


import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.commands.DriveInTeleop;
import com.concordrobotics.stronghold.general.ArcadeDriveMath;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/** 
 * We don't want to touch this yet.
 * @author Zach
 * 
 */
public class DriveTrain extends Subsystem {
	
	public enum DriveMode {tankMode, arcadeMode}
	
	protected DriveMode mode = DriveMode.tankMode; //Starts in tank mode 
	
	double zMaxAxis = 1;
	double zMaxTwist = 1;
	boolean easyControl = true;
	
	double ghettoTime = 0;
	
	protected void initDefaultCommand() {new DriveInTeleop();}
	
	public void cSwith(boolean one, boolean two)
	{
		if (ghettoTime == 0 && one && two)
		{
			ghettoTime = 200;
			this.switchDriveMode();
		}
	}
	
	public void jInput(Joystick left, Joystick right)
	{	
		switch (mode)
		{
		case tankMode:
			// TANK
			RobotMap.robotDrive.tankDrive(left, right, easyControl);
			break;
		/*case arcadeMode:
			// ARCADE
			
			ArcadeDriveMath aDrive = new ArcadeDriveMath(left, right);
			
			RobotMap.robotDrive.arcadeDrive
			(
					(aDrive.aMathAxis()),
					(aDrive.aMathTwist()),
					easyControl 
					*
					 * This will change if moving the controller
					 * more will cause the robot to move faster.
					 *
			);
			break;*/
		default:
			break;
		}
		Timer.delay(.01);
	}
	
	public void stop()
	{
		RobotMap.robotDrive.drive(0, 0);
	}
	
	/**
	 * @param If not given a boolean it will invert the current state
	 */
	public void setEasyControl()
	{
		easyControl = !easyControl;
	}
	
	public void setEasyControl(boolean easyControlActuator)
	{
		easyControl = easyControlActuator;
	}
	
	private void switchDriveMode()
	{
		switch(mode)
		{
			case tankMode:
				this.setDriveMode(DriveMode.arcadeMode);
				break;
			case arcadeMode:
				this.setDriveMode(DriveMode.tankMode);
				break;
		}
	}
	
	public void setDriveMode(DriveMode dMode) {
		mode = dMode;
	}
}
