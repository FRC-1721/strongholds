package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */
public class AutoLowBar extends CustomCommandGroup {

    public  AutoLowBar() {
    	addSequential(new PrintCommand("In Auto"));
    	if (RobotMap.useDrivePIDinAuto) {
    		addSequential(new EnableDrivePIDCommand());
    		addSequential(new PrintCommand("Enabling PID"));
    	}
    	
    	addSequential(new PrintCommand("Setting Yaw"));
    	addSequential(new SetCurrentYaw(180));
    	addSequential(new PrintCommand("Turning 0"));
    	
    	//addParallel(new SetPitchAngle(5));
    	addSequential(new TurnRelative(0.0, 5)); 
    	addSequential(new PrintCommand("Driving"));
    	addSequential(new DistanceDriveStraight(-10.0,-0.7));
    	addSequential(new PrintCommand("Drive to coordinates"));
    	
    	addParallel(new SetPitchAngle(RobotMap.autoShootAngle));
    	//addSequential(new DriveToCoordinates(RobotMap.xAutoShootPosition, RobotMap.yAutoShootPosition, 0.8));
    	addSequential(new TurnAbsolute(RobotMap.autoDriveAngle, 3));
    	addSequential(new DistanceDriveStraight(RobotMap.autoDriveDistance, -0.8));
    	addSequential(new TurnAbsolute(RobotMap.autoDriveAngle2, 3));
    	addSequential(new ThrowBallCommand());
    	addParallel(new SetPitchAngle(5.0));
    	//addSequential(new DriveToCoordinates(Robot.getStationX(1), 10.0, -0.8) );
    }
    
    public void addCommands() {
    	if (commandsAdded) return;
    }
}
