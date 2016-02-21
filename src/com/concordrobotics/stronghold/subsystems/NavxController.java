/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2016. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.concordrobotics.stronghold.subsystems;

import com.concordrobotics.stronghold.CustomPIDController;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.tables.ITable;
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
public class NavxController extends Subsystem implements Sendable {

  /** The internal {@link CustomPIDController} */
  private CustomPIDController controller;

  private AHRS mGyro;
  
  static final double kToleranceDegrees = 2.0f;
  
  protected double pidOut;
  /** An output which calls {@link PIDCommand#usePIDOutput(double)} */
  private PIDOutput output = new PIDOutput() {
    public void pidWrite(double output) {
      usePIDOutput(output);
    }
  };


  /**
   * Instantiates a {@link NavxController} that will use the given p, i and d
   * values.
   *$
   * @param name the name
   * @param p the proportional value
   * @param i the integral value
   * @param d the derivative value
   */
  public NavxController(String name, double p, double i, double d, double f, AHRS gyro) {
    super(name);
    mGyro = gyro;
    controller = new CustomPIDController(p, i, d, mGyro, output);
    controller.setInputRange(-180.0f,  180.0f);
    controller.setOutputRange(-1.0, 1.0);
    controller.setAbsoluteTolerance(kToleranceDegrees);
    controller.setContinuous(true);
    mGyro.setPIDSourceType(PIDSourceType.kDisplacement);
    mGyro.zeroYaw();
  }

  /**
   * Returns the {@link CustomPIDController} used by this {@link NavxController}. Use
   * this if you would like to fine tune the pid loop.
   *
   * @return the {@link PIDController} used by this {@link NavxController}
   */
  public CustomPIDController getPIDController() {
    return controller;
  }

  
  public void initDefaultCommand() {
      // Set the default command for a subsystem here.
      //setDefaultCommand(new MySpecialCommand());
  }

  
  protected void usePIDOutput(double output) {
      // Use output to drive your system, like a motor
      // e.g. yourMotor.set(output);
	  pidOut = output;
  }

  /**
   * Adds the given value to the setpoint. If
   * {@link NavxController#setInputRange(double, double) setInputRange(...)} was
   * used, then the bounds will still be honored by this method.
   *$
   * @param deltaSetpoint the change in the setpoint
   */
  public void setSetpointRelative(double deltaSetpoint) {
    setSetpoint(getPosition() + deltaSetpoint);
  }

  /**
   * Sets the setpoint to the given value. If
   * {@link NavxController#setInputRange(double, double) setInputRange(...)} was
   * called, then the given setpoint will be trimmed to fit within the range.
   *$
   * @param setpoint the new setpoint
   */
  public void setSetpoint(double setpoint) {
    controller.setSetpoint(setpoint);
  }

  /**
   * Returns the setpoint.
   *$
   * @return the setpoint
   */
  public double getSetpoint() {
    return controller.getSetpoint();
  }

  /**
   * Returns the current position
   *$
   * @return the current position
   */
  public double getPosition() {
    return mGyro.getYaw();
  }

  /**
   * Sets the maximum and minimum values expected from the input.
   *
   * @param minimumInput the minimum value expected from the input
   * @param maximumInput the maximum value expected from the output
   */
  public void setInputRange(double minimumInput, double maximumInput) {
    controller.setInputRange(minimumInput, maximumInput);
  }

  /**
   * Sets the maximum and minimum values to write.
   *
   * @param minimumOutput the minimum value to write to the output
   * @param maximumOutput the maximum value to write to the output
   */
  public void setOutputRange(double minimumOutput, double maximumOutput) {
    controller.setOutputRange(minimumOutput, maximumOutput);
  }

  
  public double getPIDOutput() {
	  return pidOut;
  }
  /**
   * Set the absolute error which is considered tolerable for use with OnTarget.
   * The value is in the same range as the PIDInput values.
   *$
   * @param t the absolute tolerance
   */
  public void setAbsoluteTolerance(double t) {
    controller.setAbsoluteTolerance(t);
  }

  /**
   * Set the percentage error which is considered tolerable for use with
   * OnTarget. (Value of 15.0 == 15 percent)
   *$
   * @param p the percent tolerance
   */
  public void setPercentTolerance(double p) {
    controller.setPercentTolerance(p);
  }

  /**
   * Return true if the error is within the percentage of the total input range,
   * determined by setTolerance. This assumes that the maximum and minimum input
   * were set using setInput.
   *$
   * @return true if the error is less than the tolerance
   */
  public boolean onTarget() {
    return controller.onTarget();
  }

  
  /**
   * Enables the internal {@link PIDController}
   */
  public void enable() {
    controller.enable();
  }

  /**
   * Disables the internal {@link PIDController}
   */
  public void disable() {
    controller.disable();
  }


  public String getSmartDashboardType() {
    return "NavxController";
  }

  public void initTable(ITable table) {
    controller.initTable(table);
    super.initTable(table);
  }
  
  public void updateSmartDashboard() {
	  SmartDashboard.putNumber("NavController/Heading", mGyro.getAngle());
	  SmartDashboard.putNumber("NavController/PIDOutput", pidOut);
	  SmartDashboard.putNumber("NavControlelr/X", mGyro.getDisplacementX());
	  SmartDashboard.putNumber("NavControlelr/Y", mGyro.getDisplacementX());
	  SmartDashboard.putNumber("NavControlelr/Z", mGyro.getDisplacementX());
	  
  }
}
