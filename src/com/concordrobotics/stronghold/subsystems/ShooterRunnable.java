package com.concordrobotics.stronghold.subsystems;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Timer;

public class ShooterRunnable implements Runnable
{

	private Thread t;
	private String threadName;
	
	ShooterRunnable(String threadName)
	{
		this.threadName = threadName;
	}
	
	@Override
	public void run(){
		// Set the ball-throwing motors to full voltage.
		RobotMap.shootL.set(-1.0);
		RobotMap.shootR.set(1.0);
		
		// Wait for .75 seconds for the motors to get to full speed.
		Timer.delay(RobotMap.spinUp);
		
		// Kick the ball forward using the Servo motor.
		RobotMap.shootK.setAngle(120);
		//Timer.delay(1.5);
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		RobotMap.shootK.setAngle(10);
		
		// Reset both of the ball throwing motors.
		RobotMap.shootL.set(0);
		RobotMap.shootR.set(0);
	}
	
	public void start()
	{
		if(t == null)
		{
			t = new Thread(this, threadName);
			t.start();
		}
	}

}
