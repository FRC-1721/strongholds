package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetPitchAngle extends Command {

	private double angle;
	private static int kToleranceIterations = 5;


	
	public SetPitchAngle(double pitch) {
		requires(Robot.shooter);
		angle = pitch;
	}
	
	@Override
	protected void initialize() {
		Robot.shooter.enable();
		Robot.shooter.setSetpoint(angle);
		Robot.shooter.setAbsoluteTolerance(5.0);
		Robot.shooter.setToleranceBuffer(kToleranceIterations);
		
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("Pitch Angle: SETPOINT", Robot.shooter.getSetpoint());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Robot.shooter.onTargetDuringTime();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		// Let the controller continue controlling
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
