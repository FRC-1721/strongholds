package com.concordrobotics.stronghold.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowBar extends CommandGroup {
    
    public  AutoLowBar() {
    	/*
    	addSequential(new TurnRelative(0));
    	addSequential(new DistanceDriveStraight(4.0));
    	addSequential(new TurnRelative(90));
    	addSequential(new DistanceDriveStraight(4.0));
    	addSequential(new TurnRelative(90));
    	addSequential(new DistanceDriveStraight(4.0));
    	addSequential(new TurnRelative(90));
    	addSequential(new DistanceDriveStraight(4.0));
    	addSequential(new TurnRelative(90));
    */
    	addSequential(new EnableDrivePIDCommand());
    	addSequential(new EnableDriveHeadingLock());
    	addParallel(new SetPitchAngle(50));
    	addSequential(new DistanceDriveStraight((7.1+4+11)));
    }
}
