package com.concordrobotics.stronghold;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;

public class CustomAHRS extends  AHRS implements PIDSource, LiveWindowSendable {
	private float m_yawOffset = 0;
	public CustomAHRS(SPI.Port spi_port_id, byte update_rate_hz) {
		super(spi_port_id, update_rate_hz);
		// TODO Auto-generated constructor stub
	}
	
	public void setYawOffset(float yawOffset) {
		m_yawOffset = yawOffset;
	}
	
	public float getYaw() {
		float curYaw = super.getYaw() + m_yawOffset;
		if (curYaw > 180.0) {
			curYaw = curYaw - (float)360.0;
		} else if (curYaw < -180.0) {
			curYaw = curYaw + (float) 180.0;
		}
		return curYaw;
	}
	
}
