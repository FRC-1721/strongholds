package com.concordrobotics.hagrid.subsystems;

import com.concordrobotics.hagrid.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

	protected void initDefaultCommand() {}
	
	
	/**
	 * Shoot the ball forward out of the shooter.
	 * Voltage is set to 100% always.
	 * @return if the command has finished
	 */
	public void shoot() {
		
		// Set the ball-throwing motors to full voltage.
		RobotMap.shootL.set(-1.0);
		RobotMap.shootR.set(1.0);
		
		// Wait for .75 seconds for the motors to get to full speed.
		Timer.delay(RobotMap.spinUp);
		
		// Kick the ball forward using the Servo motor.
		RobotMap.shootK.setAngle(80);
		Timer.delay(.75);
		RobotMap.shootK.setAngle(0);
		
		// Reset both of the ball throwing motors.
		RobotMap.shootL.set(0);
		RobotMap.shootR.set(0);
	}	
	
	
	/**
	 * Suck the ball in while the button is held.
	 * Voltage is set in robotmap.
	 * @return if the command has finished
	 */
	public void suck() {
		RobotMap.shootL.set(RobotMap.suckLVolts);
		RobotMap.shootR.set(RobotMap.suckRVolts);
	}
	
	/**
	 * Stop the shooter motors.
	 * @return if the command has finished
	 */
	public void throwerRelease() {
		RobotMap.shootL.set(0);
		RobotMap.shootR.set(0);
	}
	
	/**
	 * Pitch the shooter up.
	 * @return if the command has finished
	 */
	public void pitch(boolean direction) {
		if (direction) {
			RobotMap.shootA.set(RobotMap.pitchUpVolts);
		} else {
			RobotMap.shootA.set(RobotMap.pitchDownVolts);
		}
	}
	
	
	public void pitchRelease() {
		RobotMap.shootA.set(0);
	}
}
