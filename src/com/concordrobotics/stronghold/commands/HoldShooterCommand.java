package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HoldShooterCommand extends Command {

	private static int kToleranceIterations = 5;


	
	public HoldShooterCommand() {
		requires(Robot.shooter);
	}
	
	@Override
	protected void initialize() {
		Robot.shooter.enable();
		Robot.shooter.setSetpointRelative(0.0);
		
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
		// Let the controller continue controlling
		//Robot.shooter.disable();
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
	}

}
