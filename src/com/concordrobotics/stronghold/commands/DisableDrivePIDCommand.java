//package com.concordrobotics.stronghold.commands;
//
//import edu.wpi.first.wpilibj.command.Command;
//import com.concordrobotics.stronghold.RobotMap;
///**
// *
// */
//public class DisableDrivePIDCommand extends Command {
//
//	protected boolean complete = false;
//	
//    public DisableDrivePIDCommand() {
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    	complete = false;
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    	RobotMap.robotDrive.disablePID();
//    	complete = true;
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return complete;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
//}
