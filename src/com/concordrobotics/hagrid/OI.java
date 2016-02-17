package com.concordrobotics.hagrid;

import com.concordrobotics.hagrid.commands.PitchDownCommand;
import com.concordrobotics.hagrid.commands.PitchReleaseCommand;
import com.concordrobotics.hagrid.commands.PitchUpCommand;
import com.concordrobotics.hagrid.commands.ReleaseThrowerCommand;
import com.concordrobotics.hagrid.commands.SuckBallCommand;
import com.concordrobotics.hagrid.commands.ThrowBallCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public static Joystick jLeft;
	public static Joystick jRight;
	public static Joystick jOperator;
	
	public static JoystickButton sucker;
	public static JoystickButton angleUp;
	public static JoystickButton angleDown;
	public static JoystickButton shooter;
	
	public OI() {
		jLeft = new Joystick(RobotMap.jLeftPort);
		jRight = new Joystick(RobotMap.jRightPort);
		jOperator = new Joystick(RobotMap.jOpPort);
		
		angleUp = new JoystickButton(jOperator, 5);
		angleDown = new JoystickButton(jOperator, 7);
		
		sucker = new JoystickButton(jOperator, 6);
		shooter = new JoystickButton(jOperator, 8);
		
		
		//####################
		// COMMAND ACTIVATION 
		//####################
		angleUp.whenPressed(new PitchUpCommand());
		angleDown.whenPressed(new PitchDownCommand());
		angleUp.whenReleased(new PitchReleaseCommand());
		angleDown.whenReleased(new PitchReleaseCommand());
		sucker.whenPressed(new SuckBallCommand());
		sucker.whenReleased(new ReleaseThrowerCommand());
		shooter.whenPressed(new ThrowBallCommand());
	}
}

