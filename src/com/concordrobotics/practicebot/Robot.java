package com.concordrobotics.practicebot;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
/**
 * Code to run a little test drive train.
 * This thing was hobbled together using whatever we had lying around.
 * Pretty sure it's Jags for motor control. Using a cRIO in java (I believe).
 * Please don't use this as an example of anything. It just needs to work once.
 */
public class Robot extends SampleRobot {
    
	final int leftChan = 1;
	final int rightChan = 2;
	final int sChanL = 1;
	final int sChanR = 2;
	
	RobotDrive drive;
	Joystick jLeft, jRight;
	Jaguar left, right;	
	
	/** Constructor that does nothing. **/
    public Robot() {}
    
    /** Runs once when the robot starts. **/
    public void robotInit() {
       jLeft = new Joystick(sChanL);
       jRight = new Joystick(sChanR);
       left = new Jaguar(leftChan);
       right = new Jaguar(rightChan);
    }
    public void autonomous() {}

    /** Drives with tank steering. Uses joystick1 as LEFT, and joystick2 as RIGHT **/
    public void operatorControl() {
        while (isOperatorControl() && isEnabled()) {
            drive.tankDrive(jLeft, jRight);
            Timer.delay(0.005);	
        }
    }
}
