package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */
public class AutoLowBar extends CustomCommandGroup {

    public  AutoLowBar() {
    	if (RobotMap.useDrivePIDinAuto) {
    		addSequential(new EnableDrivePIDCommand());
    	}
    	
    	addSequential(new SetCurrentYaw(180));
    	
    	addParallel(new SetPitchAngle(5));
    	addSequential(new TurnRelative(0.0, 0)); 
    	addSequential(new DistanceDriveStraight(-10.0,-0.7));
    	addSequential(new EnableDrivePIDCommand());
    	addSequential(new TurnAbsolute(195, 0));
    	addSequential(new DistanceDriveStraight(-3.0,-0.7));
    	addParallel(new SetPitchAngle(RobotMap.autoShootAngle));
    	//addSequential(new DriveToCoordinates(RobotMap.xAutoShootPosition, RobotMap.yAutoShootPosition, 0.8));
    	addSequential(new TurnAbsolute(RobotMap.autoDriveAngle, 3));
    	addSequential(new DistanceDriveStraight(RobotMap.autoDriveDistance, 0.8));
    	addSequential(new TurnAbsolute(RobotMap.autoDriveAngle2, 3));
    	/*addSequential(new ThrowBallCommand());
    	addParallel(new SetPitchAngle(5.0));
    	addSequential(new DriveToCoordinates(Robot.getStationX(1), 10.0, -0.8) ); */
    }
    
    public void addCommands() {
    	if (commandsAdded) return;
    }
}
