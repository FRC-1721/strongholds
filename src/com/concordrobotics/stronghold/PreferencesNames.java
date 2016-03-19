/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.concordrobotics.stronghold;

/**
 *
 * @author wegscd
 */
public class PreferencesNames {
  
  public static final String SHOOTER_HIGH_POSITION = "shooter_high_position";
  public static final String SHOOTER_LOW_POSITION = "shooter_low_position";

    public static final String AUTONOMOUS_CHOICE = "autonomous_choice";
    public static final String AUTONOMOUS_USE_DRIVE_PID = "autonomous_useDrivePID";
    public static final String LEFT_DRIVE_ENCODER_DISABLED = "left_drive_encoder_disabled";
    public static final String RIGHT_DRIVE_ENCODER_DISABLED = "right_drive_encoder_disabled";
    
    public static final String AUTONOMOUS_CHOICE_NONE = "None";
    public static final String AUTONOMOUS_CHOICE_LOW_BAR = "Low Bar";
    public static final String AUTONOMOUS_CHOICE_ROUGH_TERRAIN = "Rough Terrain";
    public static final String AUTONOMOUS_CHOICE_TEETER_TOTTER = "Teeter Totter";
    public static final String AUTONOMOUS_START_STATION = "Auto Starting Station";
    
    public static final String GYRO_RATE_P = "gyro_rate_p";
    public static final String GYRO_RATE_D = "gyro_rate_d";
    public static final String GYRO_RATE_F = "gyro rate_f";
    
    public static final String GYRO_HEADING_P = "gyro_heading_p";
    public static final String GYRO_HEADING_I = "gyro_heading_i";
    public static final String GYRO_HEADING_D = "gyro_heading_d";

    public static final String DRIVE_PID_P = "drive_pid_p";
    public static final String DRIVE_PID_D = "drive_pid_d";
    public static final String DRIVE_PID_F = "drive_pid_f";
    
    public static final String DIST_PID_P = "dist_PID_p";
    public static final String DIST_PID_I = "dist_PID_i";
    public static final String DIST_PID_D = "dist_PID_d";   
  
    
    public static final String[] AUTONOMOUS_ALL_CHOICES = new String[]{
        AUTONOMOUS_CHOICE_NONE,
        AUTONOMOUS_CHOICE_LOW_BAR,
        AUTONOMOUS_CHOICE_ROUGH_TERRAIN,
        AUTONOMOUS_CHOICE_TEETER_TOTTER
    };
    
    
  
}
