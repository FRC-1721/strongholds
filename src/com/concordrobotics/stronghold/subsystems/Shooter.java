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
	 * Speed up the motors for x seconds, eject the ball, wait again, retract the motors and turn off the spinners.
	 * This command is activated when button is pressed by human op.
	 * @author Brennan
	 */
	public void shoot() {
		
		// Set the motors to spin the ball out.
		RobotMap.shootL.set(-1.0);
		RobotMap.shootR.set(1.0);
		
		// Let the motors spin to full speed.
		Timer.delay(RobotMap.sSpinupTime);
		
		// Push the ball forward with the servo.
		RobotMap.shootK.setAngle(80);
		Timer.delay(2.0);
		RobotMap.shootK.setAngle(0);
		
		// Turn off the spinning motors.
		RobotMap.shootL.set(0);
		RobotMap.shootR.set(0);
	}
	
	/**
	 * Takes the ball in to the cannon, doesn't need to spin up though.
	 * Should activate while a button is being held down (by human op)
	 * @author Brennan
	 */
	public void suck() {
		RobotMap.shootL.set(.4);
		RobotMap.shootR.set(-.5);
		
	}
	
	/**
	 * Stop the spinning wheel motors from spinning.
	 * Needed to be used when a suck command is canceled (release the button)
	 * @author Brennan
	 */
	public void shootRelease() {
		RobotMap.shootL.set(0);
		RobotMap.shootR.set(0);
	}
	
	/**
	 * Angle the shooter towards the sky
	 * Runs the motor so that the shooter aims up - speed defined in RobotMap
	 * @author Brennan
	 */
	public void pitchUp() {
		// TODO: Calibrate encoder and ad encoder-stop.
		RobotMap.shootA.set(RobotMap.pitchUpVolts);
	}
	
	/**
	 * Angle the shooter towards the ground
	 * Runs the motor so that the shooter aims down - speed defined in RobotMap.
	 * @author Brennan
	 */
	public void pitchDown() {
		// TODO: Calibrate encoder and ad encoder-stop.
		RobotMap.shootA.set(RobotMap.pitchDwnVolts);
	}
	
	/**
	 * Stop the shooter angler from moving.
	 * Runs after the command is done.
	 * @author Brennan
	 */
	public void pitchRelease() {
		RobotMap.shootA.set(0);
	}
}

		