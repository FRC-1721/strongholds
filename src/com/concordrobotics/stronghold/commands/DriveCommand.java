package com.concordrobotics.stronghold.commands;

import com.concordrobotics.stronghold.OI;
import com.concordrobotics.stronghold.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCommand extends Command{
	
	private final DriveTrain drive = new DriveTrain();
	private final OI oi;

	public DriveCommand() { 
		oi = new OI();
		requires(drive); 
	}
	protected void execute() { drive.jInput(oi.jLeft, oi.jRight, 1); } // Just set to run tank.
	protected void end() { drive.stop(1); } // Just set to tank.
	protected void interrupted() { end(); }
	
	/* Unused, required methods. Pfffft */
	protected void initialize() {}
	protected boolean isFinished() {return false;}
	
}
