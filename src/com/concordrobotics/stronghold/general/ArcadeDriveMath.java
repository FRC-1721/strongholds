package com.concordrobotics.stronghold.general;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class ArcadeDriveMath
{
	/* Zach why is this format so bad */
	private double 
	xAxisOne,
	xAxisTwo,
	
	twistOne,
	twistTwo;
	/* What the heck even is this. */
	
	public ArcadeDriveMath(Joystick one, Joystick two)
	{
		xAxisOne = one.getAxis(AxisType.kX);
		xAxisTwo = two.getAxis(AxisType.kX);
		
		twistOne = one.getTwist();
		twistTwo = two.getTwist();
	}
	
	private double aMathMinMax(double one, double two)
	{
		double douAMath;
		
		douAMath = Math.max(one, two);
		if(douAMath > 0)
		{return douAMath;}
		
		douAMath = Math.min(one, two);
		if(douAMath < 0)
		{return douAMath;}
		
		return 0;
	}
	
	public double aMathAxis()
	{
		return aMathMinMax(xAxisOne, xAxisTwo);
	}
	
	public double aMathTwist()
	{
		
		return aMathMinMax(twistOne, twistTwo);
	}
}
