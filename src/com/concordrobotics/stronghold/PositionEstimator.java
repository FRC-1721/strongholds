package com.concordrobotics.stronghold;

import java.util.TimerTask;

import com.concordrobotics.stronghold.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import com.kauailabs.navx.frc.AHRS;

public class PositionEstimator {

	public static final double kDefaultPeriod = .05;
	public static final double kGravity = 32.2;
	
	private double m_period = kDefaultPeriod;
	java.util.Timer m_peLoop;
	Timer m_resetTimer;
	private double lastVelEst[] = new double[2];
	private double lastPosEst[] = new double[2];
	private double lastHeading = 0.0;
	private double lastAccelEst[] = new double[2];
	private double lastVelEnc[] = new double[2];
	private double lastSEnc = 0.0;
	private Encoder m_ltEncoder;
	private Encoder m_rtEncoder;
	private AHRS m_navx;
	private double gyroGain[] = new double[6];
	private double encGain[] = new double[6];
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
	      m_peLoop.schedule(new PositionEstimatorTask(this), 0L, (long) (m_period * 1000));
	      // Initialize all arrays
	      for ( int i = 0; i < 2; i++ ) {
	    	  lastVelEst[i] = 0.0;
	    	  lastPosEst[i] = 0.0;
	    	  lastVelEnc[i] = 0.0;
	    	  lastAccelEst[i] = 0.0;
	      }
	      for ( int i = 0; i < 6; i++ ) {
	    	  gyroGain[i] = 0.2;
	    	  encGain[i] = 0.8;
	      }  
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
			  accelEnc[i] = (velEnc[i] - lastVelEnc[i])*m_rDelT;
			  posEnc[i] = lastPosEst[i] + velEnc[i]*m_period + 0.5*m_period*m_period*accelEnc[i];
			  lastVelEnc[i] = velEnc[i];
		  }
		  
		  // Calculate Gyro's measurements
		  // Need to handle the sign properly
		  double rawGyroX = -m_navx.getRawAccelX()*kGravity;
		 // double rawGyroY = m_navx.getRawAccelY()*kGravity;
		  double accelGyro[] = new double[2];
		  // Leave out side accel for now
		  accelGyro[0] = rawGyroX*Math.cos(lastHeading);
		  accelGyro[1] = rawGyroX*Math.sin(lastHeading);
		  double posGyro[] = new double[2];
		  double velGyro[] = new double[2];
		  for ( int i = 0; i < 2; i++ ) {
			  velGyro[i] = lastVelEst[i] + accelGyro[i]*m_period;
			  posGyro[i] = lastPosEst[i] + lastVelEnc[i]*m_period + 0.5*m_period*m_period*accelGyro[i];
		  }
		  
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
			  accelEst[i] = accelEst[i] + encGain[i]*(accelEnc[i] - accelEst[i]) 
					                    + gyroGain[i]*(accelGyro[i] - accelEst[i]);
			  velEst[i] = velEst[i] + encGain[i+2]*(velEnc[i] - velEst[i]) 
	                    + gyroGain[i+2]*(velGyro[i] - velEst[i]);
			  posEst[i] = posEst[i] + encGain[i+4]*(posEnc[i] - posEst[i]) 
	                    + gyroGain[i+2]*(posGyro[i] - posEst[i]);
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
}
