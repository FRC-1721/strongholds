package com.concordrobotics.stronghold.subsystems;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * The subsystem for the shooter object, to eject and take in balls.
 * @author Brennan
 *
 */
public class Shooter extends Subsystem {

	protected void initDefaultCommand() {}
	
	/**
	 * Shoot the ball out of the cannon, assuming the ball is already there.
	 * Speed up the motors for .75 seconds, eject the ball, wait again, retract the motors and turn off the spinners.
	 * @author Brennan
	 */
	public void shoot() {
		RobotMap.shootL.set(-1);
		RobotMap.shootR.set(1);
		// SLEEP THREAD
		Timer.delay(RobotMap.sSpinupTime);
		// ACTUATE SERVO FORWARD
		// TODO: Servo
		while (true) { if (true/*servo ping*/) { break; }}
		// TODO: Bring servo back
		while (true) { if (true/*servo ping*/) { break; }}
		RobotMap.shootL.set(0);
		RobotMap.shootR.set(0);
	}
	
	/**
	 * Takes the ball in to the cannon, doesn't need to spin up though.
	 * This should ONLY activate while this happens
	 * @author Brennan
	 */
	public void suck() {
		RobotMap.shootL.set(.4);
		RobotMap.shootR.set(-5);
		
	}
	public void released() {
		RobotMap.shootL.set(0);
		RobotMap.shootR.set(0);
	}
}

		