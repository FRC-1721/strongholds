package com.concordrobotics.stronghold;

import java.util.TimerTask;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class PositionEstimator {

	public static final double kDefaultPeriod = .03;
	public static final double kGravity = 32.174;
	
	private double m_period = kDefaultPeriod;
	java.util.Timer m_peLoop;
	Timer m_resetTimer;
	private double lastVelEst[] = new double[2];
	private double lastPosEst[] = new double[2];
	private double lastHeading = 0.0;
	private double lastAccelEst[] = new double[2];
	private double lastSEnc = 0.0;
	private Encoder m_ltEncoder;
	private Encoder m_rtEncoder;
	private AHRS m_navx;
	private double encGain = 0.0;
	private double m_rDelT;
	
	public PositionEstimator (double period) {

		m_ltEncoder = RobotMap.dtLeftEnc;
		m_rtEncoder = RobotMap.dtRightEnc;
		m_navx = RobotMap.navx;
		
	      m_peLoop = new java.util.Timer();
	      m_resetTimer = new Timer();
	      m_resetTimer.start();
	      
	      m_period = period;
	      m_rDelT = 1.0/period;
	      // Initialize all arrays
	      for ( int i = 0; i < 2; i++ ) {
	    	  lastVelEst[i] = 0.0;
	    	  lastPosEst[i] = 0.0;
	    	  lastAccelEst[i] = 0.0;
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
	  

	  private void calculate() {
		  // Don't start taking data until calibration is done
		  if (m_navx.isCalibrating()) return;
		  // Treate the gyro heading as gospel
		  double curHeading = Math.toRadians(m_navx.getYaw());
		  
		  // Calculate Encoders measurements
		  double encS = 0.5*(m_rtEncoder.getDistance() + m_ltEncoder.getDistance());
		  double encDelS =  encS - lastSEnc;
		  lastSEnc = encS;
		  double speedEnc = encDelS*m_rDelT;
		  double velEnc[] = new double[2];

		  velEnc[0] = Math.cos(lastHeading)*speedEnc;
		  velEnc[1] = Math.sin(lastHeading)*speedEnc;
		  double accelEnc[] = new double[2];
		  double posEnc[] = new double[2];
		  for ( int i = 0; i < 2; i++ ) {
			  posEnc[i] = lastPosEst[i] + velEnc[i]*m_period + 0.5*m_period*m_period*accelEnc[i];
		  }
		  
		  // Calculate Gyro's measurements
		  // Need to handle the sign properly
		  double rawGyroX = m_navx.getWorldLinearAccelX()*kGravity;
		 // double rawGyroY = m_navx.getRawAccelY()*kGravity;
		  double accelGyro[] = new double[2];
		  // Leave out side accel for now
		  accelGyro[0] = rawGyroX*Math.cos(lastHeading);
		  accelGyro[1] = rawGyroX*Math.sin(lastHeading);

		  
		  // Calculate the estimated quantities assuming no other changes
		  double posEst[] = new double[2];
		  double velEst[] = new double[2];
		  double accelEst[] = new double[2];
		  for ( int i = 0; i < 2; i++ ) {
			  accelEst[i] = lastAccelEst[i];
			  velEst[i] = lastVelEst[i] + lastAccelEst[i]*m_period;
			  posEst[i] = lastPosEst[i] + lastVelEst[i]*m_period + 0.5*m_period*m_period*lastAccelEst[i];
		  }
		  
		  // Update the estimates based on the gains
		  for ( int i = 0; i < 2; i++ ) {
			  // Use gyro directly for accelleration
			  accelEst[i] = accelGyro[i];
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
	    }	    
	    
}
