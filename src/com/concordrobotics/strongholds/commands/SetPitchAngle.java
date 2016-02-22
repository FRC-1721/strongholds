package com.concordrobotics.strongholds.commands;

import com.concordrobotics.stonghold.Robot;
import com.concordrobotics.stonghold.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetPitchAngle extends Command {

	private double angle;
	
	public SetPitchAngle(double pitch) {
		requires(Robot.shooter);
		angle = pitch;
	}
	
	@Override
	protected void initialize() {
		Robot.shooter.enable();
		Robot.shooter.setSetpoint(angle);
		
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Pitch Angle: SETPOINT", Robot.shooter.getSetpoint());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
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
