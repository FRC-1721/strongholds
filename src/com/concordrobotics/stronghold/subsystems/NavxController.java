/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2016. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.concordrobotics.stronghold.subsystems;

import com.concordrobotics.stronghold.CustomPIDSubsystem;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * This class is designed to handle the case where there is a {@link Subsystem}
 * which uses a single {@link PIDController} almost constantly (for instance, an
 * elevator which attempts to stay at a constant height).
 *
 * <p>
 * It provides some convenience methods to run an internal {@link PIDController}
 * . It also allows access to the internal {@link PIDController} in order to
 * give total control to the programmer.
 * </p>
 *
 * @author Joe Grinstead
 */
public class NavxController extends CustomPIDSubsystem  {

  double pidOut;
  private AHRS mGyro;

  static final double kToleranceDegrees = 2.0f;
  


  /**
   * Instantiates a {@link NavxController} that will use the given p, i and d
   * values.
   *$
   * @param name the name
   * @param p the proportional value
   * @param i the integral value
   * @param d the derivative value
   */
  public NavxController(String name, double p, double i, double d, double f, AHRS gyro, PIDSourceType pidSourceType) {
    super(name, p, i, d, f);
    mGyro = gyro;
    setPIDSourceType(pidSourceType);
  }

  public void setPIDSourceType (PIDSourceType pidSourceType) {
	  super.setPIDSourceType(pidSourceType);
	  controller.setOutputRange(-0.3, 0.3);
	  if (pidSourceType == PIDSourceType.kDisplacement) {
		  controller.setInputRange(-180.0, 180.0);
		  controller.setContinuous();
	  } else {
		  controller.setInputRange(0.0, 0.0);
		  controller.setContinuous(false);
	  }	  
  } 
  
  public void initDefaultCommand() {
      // Set the default command for a subsystem here.
      //setDefaultCommand(new MySpecialCommand());
  }
  
  protected double returnPIDInput() {
      // Return your input value for the PID loop
      // e.g. a sensor, like a potentiometer:
      // yourPot.getAverageVoltage() / kYourMaxVoltage;
  	if (m_pidSourceType == PIDSourceType.kDisplacement) {
  		return mGyro.getYaw();
  	} else {
  		return mGyro.getRawGyroZ();
  	}
  }
  
  public void setSetpointRelative(double deltaSetpoint) {
	    setSetpoint(getPosition() + deltaSetpoint);
	  }
  
  protected void usePIDOutput(double output) {
      // Use output to drive your system, like a motor
      // e.g. yourMotor.set(output);
  	pidOut = output;
  }
  
  public void reset() {
  	controller.reset();
  }
  
  public double getPIDOutput() {
  	return pidOut;
  }
  
  
  public void updateSmartDashboard() {
	  if (m_pidSourceType == PIDSourceType.kDisplacement) {
		  SmartDashboard.putNumber("NavControllerHeading", mGyro.getYaw());
	  } else {
		  SmartDashboard.putNumber("NavControllerHeadinRate", mGyro.getRawGyroZ());
	  }
	  SmartDashboard.putNumber("NavControllerPIDSetPoint", controller.getSetpoint());
	  SmartDashboard.putNumber("NavControllerPIDOutput", pidOut);
	  SmartDashboard.putNumber("NavControllerAvgError", controller.getAvgError());
  }
}
