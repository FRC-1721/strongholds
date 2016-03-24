package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCrossMoat extends CustomCommandGroup {
    public  AutoCrossMoat() {

    }
    
    public void addCommands() {
    	if (commandsAdded) return;
    	if (RobotMap.useDrivePIDinAuto) {
    		addSequential(new EnableDrivePIDCommand());
    	}
    	//addParallel(new SetPitchAngle(50));
    	addSequential(new TurnRelative(0, 1));
    	addSequential(new DistanceDriveStraight(16.0,0.8));
    	//addSequential(new DistanceDriveStraight(5.0,0.8));
    	//addSequential(new DistanceDriveStraight(3.0,0.2));
    	//addSequential(new DistanceDriveStraight(4.0,0.8));
    	//addSequential(new DistanceDriveStraight(3.0));
    	//addSequential(new DisableDrivePIDCommand());
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
