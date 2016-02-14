package com.concordrobotics.stronghold;

import com.concordrobotics.stronghold.commands.ShootCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
public class OI {
    public final Joystick jLeft;
    public final Joystick jRight;
    public final Joystick operator;
    public static JoystickButton trig;
    public OI() {
    	jLeft = new Joystick(RobotMap.jLeftPort);
    	jRight = new Joystick(RobotMap.jRightPort);
    	operator = new Joystick(RobotMap.jOpPort);
    	trig = new JoystickButton(jLeft, 1);
    	trig.whenPressed(new ShootCommand());

    }

}
