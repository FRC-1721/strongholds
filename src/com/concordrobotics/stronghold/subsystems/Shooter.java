package com.concordrobotics.stronghold.subsystems;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends PIDSubsystem {
	
	public double servoAngle = 10;
	protected Timer shootTimer;
	public enum ShootMode {
		spinUp, sucking, shooting, none
	}
	public ShootMode shootMode = ShootMode.none;
	public Shooter(double p, double i, double d) {
		super(p, i, d);
		this.setOutputRange(-.3, .4);
		shootTimer = new Timer();
		
	}

	protected void initDefaultCommand() {
		
	}
	
	/**
	 * Shoot the ball forward out of the shooter.
	 * Voltage is set to 100% always.
	 * @return if the command has finished
	 */
	public void shoot() {
		
		// Set the ball-throwing motors to full voltage.
		RobotMap.shootL.set(-1.0);
		RobotMap.shootR.set(1.0);
		shootMode = ShootMode.spinUp;
		shootTimer.reset();
	}	
	public boolean waitLoop () {
		if (shootMode == ShootMode.spinUp) {
			RobotMap.shootL.set(-1.0);
			RobotMap.shootR.set(1.0);
			if (shootTimer.hasPeriodPassed(RobotMap.spinUp)) {
				shootMode = ShootMode.shooting;
				shootTimer.reset();
			}
		}
		 if (shootMode == ShootMode.shooting) {
				RobotMap.shootL.set(-1.0);
				RobotMap.shootR.set(1.0);
				RobotMap.shootK.setAngle(120);
				if (shootTimer.hasPeriodPassed(1.5) ){
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
		SmartDashboard.putNumber("ShooterEncoder", RobotMap.shootEnc.getDistance());
	}
	
}
