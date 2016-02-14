//package com.concordrobotics.stronghold.commands;
//
//import com.concordrobotics.stronghold.RobotMap;
//import com.concordrobotics.stronghold.subsystems.Shooter;
//
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// * Pitch down command
// * Command to pitch the shooter downwards.
// * After the command is executed, the angler is released (stopped)
// * @author Brennan
// */
//
//public class PitchDownCommand extends Command {
//
//	
//	public PitchDownCommand() {
//		requires(RobotMap.shooter);
//	}
//	
//	protected void execute() {
//		RobotMap.shooter.pitchDown();
//	}
//
//	protected void end() {
//		RobotMap.shooter.pitchRelease();
//	}
//
//	protected boolean isFinished() {
//		if (Shooter.pitchDown) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	protected void initialize() {}
//	protected void interrupted() {}
//	
//}
