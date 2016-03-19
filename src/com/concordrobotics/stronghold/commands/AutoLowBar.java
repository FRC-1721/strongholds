package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowBar extends CommandGroup {
    
    public  AutoLowBar() {
    	if (RobotMap.useDrivePIDinAuto) {
    		addSequential(new EnableDrivePIDCommand());
    	}
    	addSequential(new SetCurrentYaw(180));
    	//addParallel(new SetPitchAngle(5));
    	addSequential(new TurnRelative(0.0, 1));
    	addSequential(new DistanceDriveStraight(-10.0,-0.7));
    	addParallel(new TurnAbsolute(45.0, 5));
    	addParallel(new SetPitchAngle(20.0));
    	addSequential(new DistanceDriveStraight(5.0,0.8));
    	addParallel(new TurnAbsolute(90.0, 5));
    	addSequential(new ThrowBallCommand());
    	//addSequential(new DistanceDriveStraight(-6.0, -0.6));
    	//addSequential(new TurnAbsolute(45.0));
    	
    	//addSequential(new DistanceDriveStraight(-3.0));
    	//addSequential(new TurnAbsolute(180.0));
    	//addSequential(new DistanceDriveStraight(2.0));
    	//addSequential(new TurnAbsolute(-90.0));    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    }
}
