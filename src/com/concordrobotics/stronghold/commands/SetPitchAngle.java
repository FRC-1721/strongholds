package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetPitchAngle extends Command {

	private double angle;
	private double p = 1;
	
	public SetPitchAngle(double pitch) {
		requires(Robot.shooter);
		angle = pitch;
	}
	
	@Override
	protected void initialize() {
		Robot.shooter.enable();
		Robot.shooter.setSetpoint(angle);
		Robot.shooter.setAbsoluteTolerance(1.0);
		
		
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Pitch Angle: SETPOINT", Robot.shooter.getSetpoint());
	}

	@Override
	protected boolean isFinished() {
		if(Robot.shooter.onTargetDuringTime()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
