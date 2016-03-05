package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCrossMoat extends CommandGroup {
    
    public  AutoCrossMoat() {
    	if (!RobotMap.encoderBroken) {
    		addSequential(new EnableDrivePIDCommand());
    	}
    	addSequential(new SetDriveRate(10.0));
    	addParallel(new SetPitchAngle(50));
    	addSequential(new TurnRelative(0));
    	addSequential(new PitchDriveStraight(20.0, 6.0));
    	addSequential(new DistanceDriveStraight(-2.0));
    	addSequential(new SetDriveRate(30.0));
    	addSequential(new DistanceDriveStraight(8.0));
    	addSequential(new SetDriveRate(4.0));
    	//addSequential(new DistanceDriveStraight(3.0));
    	addSequential(new DisableDrivePIDCommand());
    	addSequential(new DriveStop());
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
