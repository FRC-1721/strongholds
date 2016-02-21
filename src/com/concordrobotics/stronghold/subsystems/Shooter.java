package com.concordrobotics.stronghold.subsystems;

import com.concordrobotics.stronghold.CustomRobotDrive;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * The subsystem for the shooter object, to eject and take in balls.
 * @author Brennan
 *
 */
public class Shooter extends Subsystem {

	public static boolean shoot = false;
	public static boolean suck = false;
	
	public Shooter( ) {
		super("Shooter");
	}
	
	protected void initDefaultCommand() {}
	
	/**
	 * Shoot the ball out of the cannon, assuming the ball is already there.
	 * Speed up the motors for x seconds, eject the ball, wait again, retract the motors and turn off the spinners.
	 * This command is activated when button is pressed by human op.
	 * @author Brennan
	 */
	public void shoot() {
		
		// Set the motors to spin the ball out.
		RobotMap.shootL.set(-1);
		RobotMap.shootR.set(1);
		
		// Let the motors spin to full speed.
		Timer.delay(RobotMap.sSpinupTime);
		
		// Push the ball forward with the servo.
		RobotMap.shootK.setAngle(80);
		Timer.delay(2.0);
		RobotMap.shootK.setAngle(0);
		
		// Turn off the spinning motors.
		RobotMap.shootL.set(0);
		RobotMap.shootR.set(0);
		
		// Cancel the command.
		shoot = true;
	}
	
	/**
	 * Takes the ball in to the cannon, doesn't need to spin up though.
	 * Should activate while a button is being held down (by human op)
	 * @author Brennan
	 */
	public void suck() {
		RobotMap.shootL.set(.4);
		RobotMap.shootR.set(-5);
		
	}
	
	/**
	 * Stop the spinning wheel motors from spinning.
	 * Needed to be used when a suck command is canceled (release the button)
	 * @author Brennan
	 */
	public void release() {
		RobotMap.shootL.set(0);
		RobotMap.shootR.set(0);
	}
}

		