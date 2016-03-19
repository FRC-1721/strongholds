package com.concordrobotics.stronghold.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;
import com.concordrobotics.stronghold.subsystems.DriveTrain;
/**
 *
 */
public class SetDriveReversed extends Command {
	protected boolean newDirection = true;
	protected boolean m_reversed;
	protected Timer newDirectionTimer;
	protected static double kStopTime = 0.2;
	
    public SetDriveReversed(boolean reversed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	m_reversed = reversed;
    	newDirection = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	newDirection = Robot.driveTrain.setReversed(m_reversed);
		newDirectionTimer = new Timer();
		newDirectionTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (newDirection) Robot.driveTrain.stop();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
      if (! newDirection) {
    	  return true;
      } else if (newDirectionTimer.get() > kStopTime) {
    	  return true;
      } else {
    	  return false;
      }
    }

    // Called once after isFinished returns true
    protected void end() {
 
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
