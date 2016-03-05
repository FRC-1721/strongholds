package com.concordrobotics.stronghold.subsystems;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Shooter extends PIDSubsystem {

	public Shooter(double p, double i, double d) {
		super(p, i, d);
		this.setOutputRange(-.3, .4);
		
	}

	protected void initDefaultCommand() {
		
	}
	
	/**
	 * Shoot the ball forward out of the shooter.
	 * Voltage is set to 100% always.
	 * @return if the command has finished
	 */
	public void shoot() {
		
//		// Set the ball-throwing motors to full voltage.
//		RobotMap.shootL.set(-1.0);
//		RobotMap.shootR.set(1.0);
//		
//		// Wait for .75 seconds for the motors to get to full speed.
//		Timer.delay(RobotMap.spinUp);
//		
//		// Kick the ball forward using the Servo motor.
//		RobotMap.shootK.setAngle(120);
//		Timer.delay(1.5);
//		RobotMap.shootK.setAngle(10);
//		
//		// Reset both of the ball throwing motors.
//		RobotMap.shootL.set(0);
//		RobotMap.shootR.set(0);
		
		ShooterRunnable sr = new ShooterRunnable("SR");
		sr.start();
		
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

	
	@Override
	protected double returnPIDInput() {
		return RobotMap.shootEnc.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.shootA.set(output);
	}
	
	public void updateSmartDashboard() {

	}
	
}
