package com.concordrobotics.stronghold;

import java.util.TimerTask;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class PositionEstimator {

	public static final double kDefaultPeriod = .01;
	public static final double kGravity = 32.174;
	// ToDo get actual wheel base
	private static final double kWheelBase = 4.0;
	private double m_period = kDefaultPeriod;
	java.util.Timer m_peLoop;
	Timer m_resetTimer;
	private double lastVelEst[] = new double[2];
	private double lastPosEst[] = new double[2];
	private double lastEncS[] = new double[2];
	private double lastHeading = 0.0;
	private double lastAccelEst[] = new double[2];
	private Encoder m_ltEncoder;
	private Encoder m_rtEncoder;
	private AHRS m_navx;
	private double encGain = 1.0;
	private double gyroGain = 0.05;
	private double deltaT = 0.0;
	
	public PositionEstimator (double period) {

		m_ltEncoder = RobotMap.dtLeftEnc;
		m_rtEncoder = RobotMap.dtRightEnc;
		m_navx = RobotMap.navx;
		
	      m_peLoop = new java.util.Timer();
	      m_resetTimer = new Timer();
	      m_resetTimer.start();
	      
	      m_period = period;
	      // Initialize all arrays
	      for ( int i = 0; i < 2; i++ ) {
	    	  lastVelEst[i] = 0.0;
	    	  lastPosEst[i] = 0.0;
	    	  lastAccelEst[i] = 0.0;
	    	  lastEncS[0] = m_ltEncoder.getDistance();
	    	  lastEncS[1] = m_rtEncoder.getDistance();
	      } 	      
	      m_peLoop.schedule(new PositionEstimatorTask(this), 0L, (long) (m_period * 1000));
	      
	}
	
	public PositionEstimator ( ) {
		this(kDefaultPeriod);
	}
	
	
	  private class PositionEstimatorTask extends TimerTask {

		    private PositionEstimator m_positionEstimator;

		    public PositionEstimatorTask(PositionEstimator positionEstimator) {
		      if (positionEstimator == null) {
		        throw new NullPointerException("Given Position Estimator was null");
		      }
		      m_positionEstimator = positionEstimator;
		    }

		    @Override
		    public void run() {
		    	m_positionEstimator.calculate();
		    }
		  }
	 
	  private void filteredEncoderVelocity(double deltaHeading, double vel[] ) {
		  double encS[] = new double[2];
		  encS[0] = m_ltEncoder.getDistance();
		  encS[1] = m_rtEncoder.getDistance();
		  vel[0] = (encS[0] - lastEncS[0]);
		  vel[1] = (encS[1] - lastEncS[1]);
		  lastEncS[0] = encS[0];
		  lastEncS[1] = encS[1];
		  // Check to see if encoders predict a higher turn than actual
		  // and limit the fast wheel 
		  double delHeadingEnc = (vel[0] - vel[1]) / kWheelBase; 
		  
		  // Turning Right
		  if (deltaHeading > 0) {
			  if (delHeadingEnc > deltaHeading) {
				  // Moving forward and turning faster than gyro, reduce left wheel speed
				  if (vel[1] > 0) {
					  vel[0] = vel[1] + deltaHeading*kWheelBase;
				  // Moving back and turning faster, increase right wheel speed
				  } else {
					  vel[1] = vel[0] - deltaHeading*kWheelBase;
				  }
				  
			  } 
		  } else {
			  // Turning left
			  if (delHeadingEnc < deltaHeading) {
				  // Moving forward and turning faster than gyro, reduce right wheel speed
				  if (vel[0] > 0) {
					  vel[1] = vel[0] - deltaHeading*kWheelBase;
				  // Moving back and turning faster, increase left wheel speed
				  } else {
					  vel[0] = vel[1] + deltaHeading*kWheelBase;
				  }
				  
			  } 
		  }
		  // Transform to field relative directions
		  double dSdT = 0.5*(vel[1] + vel[1])/deltaT;
		  vel[0] = Math.cos(lastHeading)*dSdT;
		  vel[1] = Math.sin(lastHeading)*dSdT;
	  }

	  private void calculate() {
		  // Don't start taking data until calibration is done
		  deltaT = m_resetTimer.get();
		  m_resetTimer.reset();
		  if (m_navx.isCalibrating()) {
			  m_resetTimer.reset();
			  return;
		  }
		  synchronized(this) {
			  // Treate the gyro heading as gospel
			  double curHeading = Math.toRadians(m_navx.getYaw());
			  
			  // Calculate Encoders measurements

			  double velEnc[] = new double[2];
			  filteredEncoderVelocity(curHeading - lastHeading, velEnc);
			  
			  double posEnc[] = new double[2];
			  for ( int i = 0; i < 2; i++ ) {
				  posEnc[i] = lastPosEst[i] + velEnc[i]*deltaT;
			  }
			  
			  // Calculate Gyro's measurements
			  // Need to handle the sign properly
			  double accelGyro[] = new double[2];
			  accelGyro[0] = m_navx.getWorldLinearAccelX()*kGravity;
			  accelGyro[1] = m_navx.getWorldLinearAccelY()*kGravity;
			  

			  
			  // Calculate the estimated quantities assuming no other changes
			  double posEst[] = new double[2];
			  double velEst[] = new double[2];
			  double accelEst[] = new double[2];
			  for ( int i = 0; i < 2; i++ ) {
				  accelEst[i] = lastAccelEst[i];
				  velEst[i] = lastVelEst[i] + lastAccelEst[i]*deltaT;
				  posEst[i] = lastPosEst[i] + lastVelEst[i]*deltaT + 0.5*deltaT*deltaT*lastAccelEst[i];
			  }
			  
			  // Update the estimates based on the gains
			  for ( int i = 0; i < 2; i++ ) {
				  // Use gyro directly for accelleration
				  accelEst[i] = (1.0-gyroGain)*accelEst[i] + gyroGain*accelGyro[i];
				  // Use encoders with gain to adjust the velocity and prevent velocity drift
				  velEst[i] = velEst[i] + encGain*(velEnc[i] - velEst[i]);
				  // Adjust position with the encoders
				  posEst[i] = posEst[i] + encGain*(posEnc[i] - posEst[i]);
				  lastAccelEst[i] = accelEst[i];
				  lastVelEst[i] = velEst[i];
				  lastPosEst[i] = posEst[i];
						  
				  // Update the gains
				  
			  }
			  
			  // Update the "last" values
			  lastHeading = curHeading;
		  }
	  }
	  public void setPosition(double x, double y) {
		  lastPosEst[0] = x;
		  lastPosEst[1] = y;
	  }
	  
	  public void zeroVelocity(double gain) {
		  lastVelEst[0] = (1.0 - gain)*lastVelEst[0];
		  lastVelEst[1] = (1.0 - gain)*lastVelEst[1];
	  }

	    public double getVelocityX() {
	        return lastVelEst[0];
	    }

	    public double getVelocityY() {
	        return lastVelEst[1];
	    }

	    public double getVelocityZ() {
	        return 0;
	    }

	    public double getDisplacementX() {
	        return lastPosEst[0];
	    }

	    public double getDisplacementY() {
	        return lastPosEst[1];
	    }
	    
	    public double getAccelX() {
	        return lastAccelEst[0];
	    }

	    public double getAccelY() {
	        return lastAccelEst[1];
	    }
	    
	    public double getDisplacementZ() {
	         return 0;
	    } 
	    
	    public void updateSmartDashboard(){
	  	  // Left side
	      SmartDashboard.putBoolean("PositionEstCalibrating", m_navx.isCalibrating());
	  	  SmartDashboard.putNumber("PositionEstX", getDisplacementX());
	  	  SmartDashboard.putNumber("PositionEstY", getDisplacementY());
	  	  SmartDashboard.putNumber("PositionEstVelX", getVelocityX());
	  	  SmartDashboard.putNumber("PositionEstVelY", getVelocityY());
	  	  SmartDashboard.putNumber("PositionEstAccelX", lastAccelEst[0]);
	  	  SmartDashboard.putNumber("PositionEstAccelY", lastAccelEst[1]);	  	  
	  	  SmartDashboard.putNumber("PositionEstDeltaT", deltaT);	
	  	  SmartDashboard.putNumber("PositionEstYaw", m_navx.getYaw());
	    }	    
	    
}
