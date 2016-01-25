package com.concordrobotics.stronghold;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
    Joystick jLeft = new Joystick(RobotMap.jLeftPort);
    Joystick jRight = new Joystick(RobotMap.jRightPort);
    
    
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    // button.whenPressed(new ExampleCommand());
    // button.whileHeld(new ExampleCommand());
    // button.whenReleased(new ExampleCommand());
}

