//package com.concordrobotics.stronghold.commands;
//
//import com.concordrobotics.stronghold.RobotMap;
//import com.concordrobotics.stronghold.subsystems.Shooter;
//
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// * Command to pitch the shooter up.
// * After the command is executed, the motors are released (turned off)
// * 
// * @author Brennan
// */
//public class PitchUpCommand extends Command {
//	
//	
//	
//	public PitchUpCommand() {
//		requires(RobotMap.shooter);
//	}
//	
//	protected void execute() {
//		RobotMap.shooter.pitchUp();
//	}
//
//	protected void end() {
//		RobotMap.shooter.pitchRelease();
//	}
//
//	protected boolean isFinished() {
//		if (Shooter.pitchUp) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	protected void initialize() {}
//	protected void interrupted() {}
//}
