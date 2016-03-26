package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.Robot;
import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCrossMoat extends CommandGroup {
    public  AutoCrossMoat() {
    	if (RobotMap.useDrivePIDinAuto) {
    		addSequential(new EnableDrivePIDCommand());
    	}
    	//addParallel(new SetPitchAngle(50));
    	addSequential(new TurnRelative(0, 1));
    	addSequential(new DistanceDriveStraight(-10.0,-0.85));
    	addSequential(new EnableDrivePIDCommand());
    	addSequential(new DistanceDriveStraight(2.0,0.6));
    	addSequential(new DistanceDriveStraight(6.0,0.9));

    	addParallel(new SetPitchAngle(RobotMap.autoShootAngle));
    	addSequential(new DriveToCoordinates(RobotMap.xAutoShootPosition, RobotMap.yAutoShootPosition, 0.8));

    	addSequential(new TurnAbsolute(RobotMap.autoDriveAngle2, 3));
    	addSequential(new ThrowBallCommand());
    	addParallel(new SetPitchAngle(5.0));
    	addSequential(new DriveToCoordinates(Robot.getStationX(1), 10.0, -0.7));
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
