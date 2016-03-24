package com.concordrobotics.stronghold.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.concordrobotics.stronghold.commands.LastMinuteCommandGroup;

public abstract class CustomCommandGroup extends CommandGroup implements LastMinuteCommandGroup {

	public CustomCommandGroup() {
		// TODO Auto-generated constructor stub
	}

	public CustomCommandGroup(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
}
