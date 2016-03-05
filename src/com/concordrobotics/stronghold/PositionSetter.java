package com.concordrobotics.stronghold;

public class PositionSetter {
	private int m_station;
	public static final double kDefenseSpacing = 4.0 + 2.0/12.0;
	PositionSetter(int station) {
		m_station = station;
	}

	void set() {
		double x = 0; // on centerline
		double y = ((double) m_station - 0.5)*kDefenseSpacing;
		Robot.positionEstimator.setPosition(x, y);
	}
}
