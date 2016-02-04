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

    public JoystickButton getBtn(int joyid, int button) {
    	switch (joyid) {
    	case RobotMap.jLeftPort:
    		return new JoystickButton(jLeft, button);
    	case RobotMap.jRightPort:
    		return new JoystickButton(jRight, button);
    	case RobotMap.jOpPort:
    		return new JoystickButton(operator, button);
    	default:
    		return null;
    	}
    }
}
