package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.CustomPIDController;
import com.concordrobotics.stronghold.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HoldShooterCommand extends Command {

	private static int kToleranceIterations = 5;
	private Timer holdTimer;

	
	public HoldShooterCommand() {
		requires(Robot.shooter);
		holdTimer = new Timer();
		holdTimer.start();
	}
	
	@Override
	protected void initialize() {
		CustomPIDController shooterController = Robot.shooter.getPIDController();
		shooterController.setOutput(Robot.shooter.lastPower);
		Robot.shooter.enable();
		shooterController.setOutput(Robot.shooter.lastPower);
		Robot.shooter.setSetpointRelative(0.0);
		
	}

	@Override
	protected void execute() {
		Robot.shooter.waitLoop();
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
