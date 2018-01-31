package org.usfirst.frc3244.SuperSirAntsABot;

public class Constants {
	
	/**
	 * Which PID slot to pull gains from.  Starting 2018, you can choose 
	 * from 0,1,2 or 3.  Only the first two (0,1) are visible in web-based configuration.
	 */
	public static final int kSlotIdx = 0;
	
	/* Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops.  
	 * For now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/*
	 * set to zero to skip waiting for confirmation, set to nonzero to wait
	 * and report to DS if action fails.
	 */
	public static final int kTimeoutMs = 10;
	
	//PDP Channles
    public static final int DRIVE_BACK_RIGHT_PDP = 0;
	public static final int DRIVE_FRONT_RIGHT_PDP = 1;
	public static final int WINCH_PDP = 2;
	public static final int NC3 = 3;
	public static final int WRIST_PDP = 4;
	public static final int KANGAROO_POWER_PDP = 5;
	public static final int VOLTMETER_PDP = 6;
	public static final int NC7_PDP = 7;
	public static final int LIGHTS_SPIKE1_PDP = 8;
	public static final int LIGHTS_SPIKE2_PDP = 9;
	public static final int INTAKE_PDP = 10;
	public static final int FUEL_INDEXER_PDP = 11;
	public static final int NC12_PDP = 12;
	public static final int DRIVE_FRONT_LEFT_PDP = 13;
	public static final int FLYWHEEL_PDP = 14;
	public static final int DRIVE_BACK_LEFT_PDP = 15;
}
