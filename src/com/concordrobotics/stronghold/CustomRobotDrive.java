/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2016. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.concordrobotics.stronghold;


import com.concordrobotics.stronghold.subsystems.NavxController;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;



/**
 * Utility class for handling Robot drive based on a definition of the motor
 * configuration. The robot drive class handles basic driving for a robot.
 * Currently, 2 and 4 motor tank and mecanum drive trains are supported. In the
 * future other drive types like swerve might be implemented. Motor channel
 * numbers are supplied on creation of the class. Those are used for either the
 * drive function (intended for hand created drive code, such as autonomous) or
 * with the Tank/Arcade functions intended to be used for Operator Control
 * driving.
 */
public class CustomRobotDrive implements MotorSafety {

  protected MotorSafetyHelper m_safetyHelper;

  /**
   * The location of a motor on the robot for the purpose of driving
   */
  public static class MotorType {

    /**
     * The integer value representing this enumeration
     */
    public final int value;
    static final int kLeft_val = 0;
    static final int kRight_val = 1;

    /**
     * motortype: front left
     */
    public static final MotorType kLeft = new MotorType(kLeft_val);
    /**
     * motortype: front right
     */
    public static final MotorType kRight = new MotorType(kRight_val);
    

    private MotorType(int value) {
      this.value = value;
    }
  }

  public static final double kDefaultExpirationTime = 0.1;
  public static final double kDefaultSensitivity = 0.5;
  public static final double kDefaultMaxOutput = 1.0;
  protected static final int kMaxNumberOfMotors = 2;
  protected double m_sensitivity;
  protected double m_maxOutput;
  protected SpeedController m_leftMotor;
  protected SpeedController m_rightMotor;
  protected boolean m_allocatedSpeedControllers = false;
  protected byte m_syncGroup = 0;
  protected static boolean kArcadeRatioCurve_Reported = false;
  protected static boolean kTank_Reported = false;
  protected static boolean kArcadeStandard_Reported = false;
  public CustomPIDController m_leftController;
  public CustomPIDController m_rightController;
  // Default PID parameters
  protected boolean m_PIDEnabled = false; 
  // Output from -1 to 1 scaled to give rate in ft/s for PID controller
  protected double m_rateScale = 5.0;
  protected NavxController m_turnController;
  protected double m_turnDeadzone = 0.02;
  protected boolean m_headingLock = false;
 
  /**
   * Constructor for CustomRobotDrive with 2 motors specified as SpeedController
   * objects. The SpeedController version of the constructor enables programs to
   * use the CustomRobotDrive classes with subclasses of the SpeedController objects,
   * for example, versions with ramping or reshaping of the curve to suit motor
   * bias or dead-band elimination.
   *$
   * @param leftMotor The left SpeedController object used to drive the robot.
   * @param rightMotor the right SpeedController object used to drive the robot.
   */
  public CustomRobotDrive(SpeedController leftMotor, SpeedController rightMotor, 
		  CustomPIDController leftController, CustomPIDController rightController, NavxController turnController) {
    if (leftMotor == null || rightMotor == null) {
      throw new NullPointerException("Null motor provided");
    }
    if (leftController == null || rightController == null) {
        throw new NullPointerException("Null controllers provided");
      }
    m_leftMotor = leftMotor;
    m_rightMotor = rightMotor;
    m_sensitivity = kDefaultSensitivity;
    m_maxOutput = kDefaultMaxOutput;
    m_leftController = leftController;
    m_leftController.disable();
    m_rightController = rightController;
    m_rightController.disable();
    m_leftController.setPIDSourceType(PIDSourceType.kRate);
    m_rightController.setPIDSourceType(PIDSourceType.kRate);
    m_turnController = turnController;
    setupMotorSafety();
    drive(0, 0);
    
  }

 
  public void drive(double outputMagnitude, double curve) {
    double leftOutput, rightOutput;


    if (curve < 0) {
      double value = Math.log(-curve);
      double ratio = (value - m_sensitivity) / (value + m_sensitivity);
      if (ratio == 0) {
        ratio = .0000000001;
      }
      leftOutput = outputMagnitude / ratio;
      rightOutput = outputMagnitude;
    } else if (curve > 0) {
      double value = Math.log(curve);
      double ratio = (value - m_sensitivity) / (value + m_sensitivity);
      if (ratio == 0) {
        ratio = .0000000001;
      }
      leftOutput = outputMagnitude;
      rightOutput = outputMagnitude / ratio;
    } else {
      leftOutput = outputMagnitude;
      rightOutput = outputMagnitude;
    }
    setLeftRightMotorOutputs(leftOutput, rightOutput);
  }

  public CustomPIDController getPIDController(MotorType motorType) {
	  if (motorType == MotorType.kLeft) {
		  return m_leftController;
	  } else {
		  return m_rightController;
	  }
  }
  
  public void enablePID() {
	  m_PIDEnabled = true;

	  m_leftController.reset();
	  m_rightController.reset();
	  m_leftController.enable();
	  m_rightController.enable();
  }
  
  public void disablePID() {
	  m_PIDEnabled = false;
	  m_leftController.disable();
	  m_rightController.disable();
  }
  
  public void enableHeadingLock() {
	  m_turnController.enable();
	  m_headingLock = true;
	  // Set the setpoint to the current heading
	  m_turnController.setSetpointRelative(0.0);
  }
  
  public void disableHeadingLock() {
	  m_turnController.disable();
	  m_headingLock = false;
  }
  
  /**
   * Provide tank steering using the stored robot configuration. drive the robot
   * using two joystick inputs. The Y-axis will be selected from each Joystick
   * object.
   *$
   * @param leftStick The joystick to control the left side of the robot.
   * @param rightStick The joystick to control the right side of the robot.
   */
  public void tankDrive(GenericHID leftStick, GenericHID rightStick) {
    if (leftStick == null || rightStick == null) {
      throw new NullPointerException("Null HID provided");
    }
    tankDrive(-leftStick.getY(), -rightStick.getY(), true);
  }

  /**
   * Provide tank steering using the stored robot configuration. drive the robot
   * using two joystick inputs. The Y-axis will be selected from each Joystick
   * object.
   *$
   * @param leftStick The joystick to control the left side of the robot.
   * @param rightStick The joystick to control the right side of the robot.
   * @param squaredInputs Setting this parameter to true decreases the
   *        sensitivity at lower speeds
   */
  public void tankDrive(GenericHID leftStick, GenericHID rightStick, boolean squaredInputs) {
    if (leftStick == null || rightStick == null) {
      throw new NullPointerException("Null HID provided");
    }
    tankDrive(-leftStick.getY(), -rightStick.getY(), squaredInputs);
  }

  /**
   * Provide tank steering using the stored robot configuration. This function
   * lets you pick the axis to be used on each Joystick object for the left and
   * right sides of the robot.
   *$
   * @param leftStick The Joystick object to use for the left side of the robot.
   * @param leftAxis The axis to select on the left side Joystick object.
   * @param rightStick The Joystick object to use for the right side of the
   *        robot.
   * @param rightAxis The axis to select on the right side Joystick object.
   */
  public void tankDrive(GenericHID leftStick, final int leftAxis, GenericHID rightStick,
      final int rightAxis) {
    if (leftStick == null || rightStick == null) {
      throw new NullPointerException("Null HID provided");
    }
    tankDrive(-leftStick.getRawAxis(leftAxis), -rightStick.getRawAxis(rightAxis), true);
  }

  /**
   * Provide tank steering using the stored robot configuration. This function
   * lets you pick the axis to be used on each Joystick object for the left and
   * right sides of the robot.
   *$
   * @param leftStick The Joystick object to use for the left side of the robot.
   * @param leftAxis The axis to select on the left side Joystick object.
   * @param rightStick The Joystick object to use for the right side of the
   *        robot.
   * @param rightAxis The axis to select on the right side Joystick object.
   * @param squaredInputs Setting this parameter to true decreases the
   *        sensitivity at lower speeds
   */
  public void tankDrive(GenericHID leftStick, final int leftAxis, GenericHID rightStick,
      final int rightAxis, boolean squaredInputs) {
    if (leftStick == null || rightStick == null) {
      throw new NullPointerException("Null HID provided");
    }
    tankDrive(-leftStick.getRawAxis(leftAxis), -rightStick.getRawAxis(rightAxis), squaredInputs);
  }

  /**
   * Provide tank steering using the stored robot configuration. This function
   * lets you directly provide joystick values from any source.
   *$
   * @param leftValue The value of the left stick.
   * @param rightValue The value of the right stick.
   * @param squaredInputs Setting this parameter to true decreases the
   *        sensitivity at lower speeds
   */
  public void tankDrive(double leftValue, double rightValue, boolean squaredInputs) {

    // square the inputs (while preserving the sign) to increase fine control
    // while permitting full power
    leftValue = limit(leftValue);
    rightValue = limit(rightValue);
    if (squaredInputs) {
      if (leftValue >= 0.0) {
        leftValue = (leftValue * leftValue);
      } else {
        leftValue = -(leftValue * leftValue);
      }
      if (rightValue >= 0.0) {
        rightValue = (rightValue * rightValue);
      } else {
        rightValue = -(rightValue * rightValue);
      }
    }
    if (m_headingLock) {
    	// Check if turn requested is in the deadzone, if so, use the PID control output on top
    	// of the average value
    	if (Math.abs(leftValue - rightValue) < m_turnDeadzone) {
    		double avgValue = 0.5*(leftValue + rightValue);
    		double pidOutput = m_turnController.getPIDOutput();
    		
    		leftValue = limit(avgValue + pidOutput);
    		rightValue = limit(avgValue - pidOutput);
    	} else {
    		// A turn is requested, so set the setpoint to the current heading, and don't use the pidOutput.
    		m_turnController.setSetpointRelative(0.0);
    	}
    }
    setLeftRightMotorOutputs(leftValue, rightValue);
  }

  /**
   * Provide tank steering using the stored robot configuration. This function
   * lets you directly provide joystick values from any source.
   *$
   * @param leftValue The value of the left stick.
   * @param rightValue The value of the right stick.
   */
  public void tankDrive(double leftValue, double rightValue) {
    tankDrive(leftValue, rightValue, true);
  }

  /**
   * Arcade drive implements single stick driving. Given a single Joystick, the
   * class assumes the Y axis for the move value and the X axis for the rotate
   * value. (Should add more information here regarding the way that arcade
   * drive works.)
   *$
   * @param stick The joystick to use for Arcade single-stick driving. The
   *        Y-axis will be selected for forwards/backwards and the X-axis will
   *        be selected for rotation rate.
   * @param squaredInputs If true, the sensitivity will be decreased for small
   *        values
   */
  public void arcadeDrive(GenericHID stick, boolean squaredInputs) {
    // simply call the full-featured arcadeDrive with the appropriate values
    arcadeDrive(-stick.getY(), -stick.getX(), squaredInputs);
  }

  /**
   * Arcade drive implements single stick driving. Given a single Joystick, the
   * class assumes the Y axis for the move value and the X axis for the rotate
   * value. (Should add more information here regarding the way that arcade
   * drive works.)
   *$
   * @param stick The joystick to use for Arcade single-stick driving. The
   *        Y-axis will be selected for forwards/backwards and the X-axis will
   *        be selected for rotation rate.
   */
  public void arcadeDrive(GenericHID stick) {
    this.arcadeDrive(stick, true);
  }

  /**
   * Arcade drive implements single stick driving. Given two joystick instances
   * and two axis, compute the values to send to either two or four motors.
   *$
   * @param moveStick The Joystick object that represents the forward/backward
   *        direction
   * @param moveAxis The axis on the moveStick object to use for
   *        forwards/backwards (typically Y_AXIS)
   * @param rotateStick The Joystick object that represents the rotation value
   * @param rotateAxis The axis on the rotation object to use for the rotate
   *        right/left (typically X_AXIS)
   * @param squaredInputs Setting this parameter to true decreases the
   *        sensitivity at lower speeds
   */
  public void arcadeDrive(GenericHID moveStick, final int moveAxis, GenericHID rotateStick,
      final int rotateAxis, boolean squaredInputs) {
    double moveValue = moveStick.getRawAxis(moveAxis);
    double rotateValue = rotateStick.getRawAxis(rotateAxis);

    arcadeDrive(moveValue, rotateValue, squaredInputs);
  }

  /**
   * Arcade drive implements single stick driving. Given two joystick instances
   * and two axis, compute the values to send to either two or four motors.
   *$
   * @param moveStick The Joystick object that represents the forward/backward
   *        direction
   * @param moveAxis The axis on the moveStick object to use for
   *        forwards/backwards (typically Y_AXIS)
   * @param rotateStick The Joystick object that represents the rotation value
   * @param rotateAxis The axis on the rotation object to use for the rotate
   *        right/left (typically X_AXIS)
   */
  public void arcadeDrive(GenericHID moveStick, final int moveAxis, GenericHID rotateStick,
      final int rotateAxis) {
    this.arcadeDrive(moveStick, moveAxis, rotateStick, rotateAxis, true);
  }

  /**
   * Arcade drive implements single stick driving. This function lets you
   * directly provide joystick values from any source.
   *$
   * @param moveValue The value to use for forwards/backwards
   * @param rotateValue The value to use for the rotate right/left
   * @param squaredInputs If set, decreases the sensitivity at low speeds
   */
  public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
    // local variables to hold the computed PWM values for the motors


    double leftMotorSpeed;
    double rightMotorSpeed;

    moveValue = limit(moveValue);
    rotateValue = limit(rotateValue);
    if (m_headingLock) {
    	// Check if turn requested is in the deadzone, if so, use the PID control output for 
    	// the turn value
    	if (Math.abs(rotateValue) < m_turnDeadzone) {
    		rotateValue = m_turnController.getPIDOutput();
    	} else {
    		// A turn is requested, so set the setpoint to the current heading, and don't use the pidOutput.
    		m_turnController.setSetpointRelative(0.0);
    	}
    }
    if (squaredInputs) {
      // square the inputs (while preserving the sign) to increase fine control
      // while permitting full power
      if (moveValue >= 0.0) {
        moveValue = (moveValue * moveValue);
      } else {
        moveValue = -(moveValue * moveValue);
      }
      if (rotateValue >= 0.0) {
        rotateValue = (rotateValue * rotateValue);
      } else {
        rotateValue = -(rotateValue * rotateValue);
      }
    }

    if (moveValue > 0.0) {
      if (rotateValue > 0.0) {
        leftMotorSpeed = moveValue - rotateValue;
        rightMotorSpeed = Math.max(moveValue, rotateValue);
      } else {
        leftMotorSpeed = Math.max(moveValue, -rotateValue);
        rightMotorSpeed = moveValue + rotateValue;
      }
    } else {
      if (rotateValue > 0.0) {
        leftMotorSpeed = -Math.max(-moveValue, rotateValue);
        rightMotorSpeed = moveValue + rotateValue;
      } else {
        leftMotorSpeed = moveValue - rotateValue;
        rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
      }
    }

    setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
  }

  /**
   * Arcade drive implements single stick driving. This function lets you
   * directly provide joystick values from any source.
   *$
   * @param moveValue The value to use for fowards/backwards
   * @param rotateValue The value to use for the rotate right/left
   */
  public void arcadeDrive(double moveValue, double rotateValue) {
    this.arcadeDrive(moveValue, rotateValue, true);
  }

  
  public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
    if (m_leftMotor == null || m_rightMotor == null) {
      throw new NullPointerException("Null motor provided");
    }

    if (m_PIDEnabled) {
        m_leftController.setSetpoint(limit(leftOutput) * m_maxOutput * m_rateScale);
        m_rightController.setSetpoint(limit(rightOutput) * m_maxOutput * m_rateScale);
    } else {
        m_leftMotor.set(limit(leftOutput) * m_maxOutput, m_syncGroup);
        m_rightMotor.set(limit(rightOutput) * m_maxOutput, m_syncGroup); 	
    }


    if (this.m_syncGroup != 0) {
      CANJaguar.updateSyncGroup(m_syncGroup);
    }

    if (m_safetyHelper != null)
      m_safetyHelper.feed();
  }

  /**
   * Limit motor values to the -1.0 to +1.0 range.
   */
  protected static double limit(double num) {
    if (num > 1.0) {
      return 1.0;
    }
    if (num < -1.0) {
      return -1.0;
    }
    return num;
  }

  /**
   * Normalize all wheel speeds if the magnitude of any wheel is greater than
   * 1.0.
   */
  protected static void normalize(double wheelSpeeds[]) {
    double maxMagnitude = Math.abs(wheelSpeeds[0]);
    int i;
    for (i = 1; i < kMaxNumberOfMotors; i++) {
      double temp = Math.abs(wheelSpeeds[i]);
      if (maxMagnitude < temp)
        maxMagnitude = temp;
    }
    if (maxMagnitude > 1.0) {
      for (i = 0; i < kMaxNumberOfMotors; i++) {
        wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
      }
    }
  }

  /**
   * Rotate a vector in Cartesian space.
   */
  protected static double[] rotateVector(double x, double y, double angle) {
    double cosA = Math.cos(angle * (3.14159 / 180.0));
    double sinA = Math.sin(angle * (3.14159 / 180.0));
    double out[] = new double[2];
    out[0] = x * cosA - y * sinA;
    out[1] = x * sinA + y * cosA;
    return out;
  }

  /**
   * Invert a motor direction. This is used when a motor should run in the
   * opposite direction as the drive code would normally run it. Motors that are
   * direct drive would be inverted, the drive code assumes that the motors are
   * geared with one reversal.
   *$
   * @param motor The motor index to invert.
   * @param isInverted True if the motor should be inverted when operated.
   */
  public void setInvertedMotor(MotorType motor, boolean isInverted) {
    switch (motor.value) {
      case MotorType.kLeft_val:
        m_leftMotor.setInverted(isInverted);
        break;
      case MotorType.kRight_val:
        m_rightMotor.setInverted(isInverted);
        break;
    }
  }

  /**
   * Set the turning sensitivity.
   *
   * This only impacts the drive() entry-point.
   *$
   * @param sensitivity Effectively sets the turning sensitivity (or turn radius
   *        for a given value)
   */
  public void setSensitivity(double sensitivity) {
    m_sensitivity = sensitivity;
  }

  /**
   * Configure the scaling factor for using CustomRobotDrive with motor controllers in
   * a mode other than PercentVbus.
   *$
   * @param maxOutput Multiplied with the output percentage computed by the
   *        drive functions.
   */
  public void setMaxOutput(double maxOutput) {
    m_maxOutput = maxOutput;
  }

  /**
   * Set the number of the sync group for the motor controllers. If the motor
   * controllers are {@link CANJaguar}s, then they will all be added to this
   * sync group, causing them to update their values at the same time.
   *
   * @param syncGroup the update group to add the motor controllers to
   */
  public void setCANJaguarSyncGroup(byte syncGroup) {
    m_syncGroup = syncGroup;
  }

  /**
   * Free the speed controllers if they were allocated locally
   */
  public void free() {
    if (m_allocatedSpeedControllers) {
      if (m_leftMotor != null) {
        ((PWM) m_leftMotor).free();
      }
      if (m_rightMotor != null) {
        ((PWM) m_rightMotor).free();
      }
    }
  }

  public void setExpiration(double timeout) {
    m_safetyHelper.setExpiration(timeout);
  }

  public double getExpiration() {
    return m_safetyHelper.getExpiration();
  }

  public boolean isAlive() {
    return m_safetyHelper.isAlive();
  }

  public boolean isSafetyEnabled() {
    return m_safetyHelper.isSafetyEnabled();
  }

  public void setSafetyEnabled(boolean enabled) {
    m_safetyHelper.setSafetyEnabled(enabled);
  }

  public String getDescription() {
    return "Robot Drive";
  }

  public void stopMotor() {
    if (m_leftMotor != null) {
      m_leftMotor.set(0.0);
    }
    if (m_rightMotor != null) {
      m_rightMotor.set(0.0);
    }
    if (m_safetyHelper != null)
      m_safetyHelper.feed();
  }

  private void setupMotorSafety() {
    m_safetyHelper = new MotorSafetyHelper(this);
    m_safetyHelper.setExpiration(kDefaultExpirationTime);
    m_safetyHelper.setSafetyEnabled(true);
  }

  protected int getNumMotors() {
    int motors = 0;
    if (m_leftMotor != null)
      motors++;
    if (m_rightMotor != null)
      motors++;
    return motors;
  }
 
  public void setDriveRate(double rate) {
	  m_rateScale = rate;
  }
  
  public void updateSmartDashboard(){
	  // Left side
	  SmartDashboard.putNumber("RobotDriveLeftPIDOut", m_leftController.get());
	  SmartDashboard.putNumber("RobotDriveLeftPIDTarget", m_leftController.getSetpoint());
	  PIDSource pidSource = m_leftController.getPIDSource();
	  SmartDashboard.putNumber("RobotDriveLeftPIDSource", pidSource.pidGet());
	  SmartDashboard.putNumber("RobotDriveLeftEncoder", RobotMap.dtLeftEnc.getDistance());
	  // Right Side
	  SmartDashboard.putNumber("RobotDriveRightPIDOut", m_rightController.get());
	  SmartDashboard.putNumber("RobotDriveRightPIDTarget", m_rightController.getSetpoint());
	  pidSource = m_rightController.getPIDSource();
	  SmartDashboard.putNumber("RobotDriveRightPIDSource", pidSource.pidGet());
	  SmartDashboard.putNumber("RobotDriveRightEncoder", RobotMap.dtRightEnc.getDistance());
  }
}
