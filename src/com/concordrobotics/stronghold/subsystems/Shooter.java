package com.concordrobotics.stronghold.subsystems;

import com.concordrobotics.stronghold.CustomPIDSubsystem;
import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.commands.DriveInTeleop;
import com.concordrobotics.stronghold.commands.HoldShooterCommand;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends CustomPIDSubsystem {
	
	public double servoAngle = 10;
	protected Timer shootTimer;
	protected double shootPower = 0.0;
	public double lastPower = 0.0;
	public enum ShootMode {
		spinUp, sucking, shooting, none
	}
	public ShootMode shootMode = ShootMode.none;
	public Shooter(double p, double i, double d) {
		super(p, i, d);
		this.setOutputRange(-.8, .8);
		shootTimer = new Timer();
		shootTimer.start();
		
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new HoldShooterCommand());
	}
	
	/**
	 * Shoot the ball forward out of the shooter.
	 * Voltage is set to 100% always.
	 * @return if the command has finished
	 */
	public void shoot() {
		
		// Set the ball-throwing motors to full voltage.
	 	if (RobotMap.shootEnc.getDistance() < 45.0) {
	 		RobotMap.shootL.set(-0.5);
			RobotMap.shootR.set(0.5);
	 	} else {
	 		RobotMap.shootL.set(-0.9);
			RobotMap.shootR.set(0.9);
	 	}
		shootMode = ShootMode.spinUp;
		shootTimer.reset();
	}	
	
	public boolean waitLoop () {
		if (shootMode == ShootMode.spinUp) {
			RobotMap.shootL.set(-1.0);
			RobotMap.shootR.set(1.0);
			if (shootTimer.get() > RobotMap.spinUp) {
				shootMode = ShootMode.shooting;
				shootTimer.reset();
			}
		}
		 if (shootMode == ShootMode.shooting) {
			 	if (RobotMap.shootEnc.getDistance() < 45.0) {
			 		RobotMap.shootL.set(-0.5);
					RobotMap.shootR.set(0.5);
			 	} else {
			 		RobotMap.shootL.set(-0.9);
					RobotMap.shootR.set(0.9);
			 	}
				
				RobotMap.shootK.setAngle(110);
				if (shootTimer.get() > 1.5 ){
					shootMode = ShootMode.none;
					RobotMap.shootK.setAngle(servoAngle);
					RobotMap.shootL.set(0);
					RobotMap.shootR.set(0);
				}
			}
		if (shootMode == ShootMode.sucking) {
			RobotMap.shootL.set(RobotMap.suckLVolts);
			RobotMap.shootR.set(RobotMap.suckRVolts);	
		}
		if (shootMode == ShootMode.none) {
			RobotMap.shootK.setAngle(servoAngle);
			RobotMap.shootL.set(0.0);
			RobotMap.shootR.set(0.0);
			return true;
		} else {
			return false;
		}
	}
	
	public void shootLowGoal() {
		RobotMap.shootL.set(-.8);
		RobotMap.shootR.set(.8);
		Timer.delay(RobotMap.spinUp);
		RobotMap.shootK.setAngle(120);
		Timer.delay(1.5);
		RobotMap.shootK.setAngle(servoAngle);
	}
	
	
	/**
	 * Suck the ball in while the button is held.
	 * Voltage is set in robotmap.
	 * @return if the command has finished
	 */
	public void suck() {
		shootMode = ShootMode.sucking;
		RobotMap.shootL.set(RobotMap.suckLVolts);
		RobotMap.shootR.set(RobotMap.suckRVolts);
	}
	
	/**
	 * Stop the shooter motors.
	 * @return if the command has finished
	 */
	public void throwerRelease() {
		shootMode = ShootMode.none;
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
			lastPower = RobotMap.pitchUpVolts;
		} else {
			RobotMap.shootA.set(RobotMap.pitchDownVolts);
			lastPower = RobotMap.pitchDownVolts;
		}
	}
	
	
	public void pitchRelease() {
		RobotMap.shootA.set(0);
	}

	
	@Override
	protected double returnPIDInput() {
		if (RobotMap.shooterUsePot) {
			return RobotMap.pot.get();
		} else {
			return RobotMap.shootEnc.getDistance();
		}
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.shootA.set(output);
		shootPower=output;
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putNumber("ShooterEncoder", RobotMap.shootEnc.getDistance());
		SmartDashboard.putNumber("ShooterTimer", shootTimer.get());
		SmartDashboard.putNumber("ShooterPower", shootPower);
	}
	
}
