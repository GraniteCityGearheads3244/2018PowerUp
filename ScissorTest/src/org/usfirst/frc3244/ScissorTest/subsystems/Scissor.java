// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.ScissorTest.subsystems;

import org.usfirst.frc3244.ScissorTest.RobotMap;
import org.usfirst.frc3244.ScissorTest.commands.*;
import org.usfirst.frc3244.ScissorTest.Constants;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Scissor extends Subsystem {
	
	
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX motor_Right_Master = RobotMap.scissorMotor_Right_Master;
    private final WPI_TalonSRX motor_Left_Slave = RobotMap.scissorMotor_Left_Slave;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
	public static final double CLIMB = 12;
	public static final double SCALE = 6;
	public static final double SWITCH = 1;
	public static final double DOWN = 0;
	
	private double m_encoderUnitsPerRev = 4096;
  	private double m_userUnitsPerRev = .625;
  	private double m_encoderUnitsPeruserUnits = m_encoderUnitsPerRev/m_userUnitsPerRev;//6553.6
  	
  	private double m_targetPositionRotations = 0;
	private static final double m_maxHeight = 11.5;
	private static final double m_minHeight = 0.25;

    // member variables for Mecanum drive
  	private static final int kMaxNumberOfMotors = 2;
  	private final int m_invertedMotors[] = new int[kMaxNumberOfMotors];
  	private static final int kMotorLeft = 0;
  	private static final int kMotorRight = 1;
  	
  	// create objects needed for independent control of each wheel
  	private WPI_TalonSRX[] m_talons = new WPI_TalonSRX[kMaxNumberOfMotors];
  	private int m_absolutePosition[] = new int[kMaxNumberOfMotors];
  	private double m_zeroPositions[] = new double[kMaxNumberOfMotors];
  	
 // member variables to support closed loop mode
  	private boolean m_closedLoopMode = true;
  	private ControlMode m_closedLoopMode2018;
  	private double m_maxWheelSpeed = 445; //(10.5 Gear box = 445)//360(12.75 gear box);//550.0;     // empirically measured around 560 to 580	
  	
    public Scissor() {
    	int talonIndex = 0;

    	// construct the talons
    	m_talons[kMotorLeft] = motor_Left_Slave;
    	m_talons[kMotorRight] = motor_Right_Master;
    	
    	/* lets grab the 360 degree position of the MagEncoder's absolute position */
    	for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
    		m_absolutePosition[talonIndex] = m_talons[talonIndex].getSelectedSensorPosition(Constants.kTimeoutMs) & 0xFFF; /* mask out the bottom12 bits, we don't care about the wrap arounds */
    	}
    	
    	/* use the low level API to set the quad encoder signal */
    	for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
    		m_talons[talonIndex].setSelectedSensorPosition(m_absolutePosition[talonIndex], Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    	}
    	
        /* choose the sensor and sensor direction */
    	for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
    		m_talons[talonIndex].configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    		m_talons[talonIndex].setSensorPhase(true);
    	}
    		
        
        /* set the peak and nominal outputs, 12V means full */
    	for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
    		m_talons[talonIndex].configNominalOutputForward(0, Constants.kTimeoutMs);
    		m_talons[talonIndex].configNominalOutputReverse(0, Constants.kTimeoutMs);
    		m_talons[talonIndex].configPeakOutputForward(1, Constants.kTimeoutMs);
    		m_talons[talonIndex].configPeakOutputReverse(-1, Constants.kTimeoutMs);
    	}
    	
        /* set the allowable closed-loop error,
         * Closed-Loop output will be neutral within this range.
         * See Table in Section 17.2.1 for native units per rotation. 
         */
    	for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
    		m_talons[talonIndex].configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); /* always servo */
    	}
    	
    }
    public void init() {
		// complete initialization here that can't be performed in constructor
		// (some calls can't be made in constructor because other objects don't
		// yet exist)

		// Set up the TalonSRX closed loop / open loop mode for each wheel
		if (m_closedLoopMode) {
			setClosedLoopMode();
		} else {
			setOpenLoopMode();
		}
	}
    
    public void setClosedLoopMode() {
		m_closedLoopMode2018 = ControlMode.Position;
		
		int talonIndex = 0;
		m_closedLoopMode = true;
		setPIDF();
		/*for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			m_talons[talonIndex].changeControlMode(TalonControlMode.Speed);
			m_talons[talonIndex].enableControl();
			
		}*/
		
	}
    public void setOpenLoopMode() {
		m_closedLoopMode2018 = ControlMode.PercentOutput;
		/*
		int talonIndex = 0;
		m_closedLoopMode = false;
		for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
			m_talons[talonIndex].changeControlMode(TalonControlMode.PercentVbus);
			m_talons[talonIndex].enableControl();
		}
		*/
	}

    public void setPIDF() {
    	int talonIndex = 0;
		double scissorkP = 0.5;///RobotPreferences.getScissorkP();
		double scissorkI = 0.0;//RobotPreferences.getScissorkI();
		double scissorkD = 0.0;//RobotPreferences.getScissorkD();
		double scissorkF = 0.0;//RobotPreferences.getScissorkF();
		
    	/* set closed loop gains in slot0 */
    	for (talonIndex = 0; talonIndex < kMaxNumberOfMotors; talonIndex++) {
    		m_talons[talonIndex].config_kF(Constants.kPIDLoopIdx, scissorkF, Constants.kTimeoutMs);
    		m_talons[talonIndex].config_kP(Constants.kPIDLoopIdx, scissorkP, Constants.kTimeoutMs);
    		m_talons[talonIndex].config_kI(Constants.kPIDLoopIdx, scissorkI, Constants.kTimeoutMs);
    		m_talons[talonIndex].config_kD(Constants.kPIDLoopIdx, scissorkD, Constants.kTimeoutMs);
    	}
    }
    
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void myMotorsRun(double speed, double correction) {
    	motor_Right_Master.set(ControlMode.PercentOutput, speed + correction);
    	motor_Left_Slave.set(ControlMode.PercentOutput, speed - correction);
    }
    
    public double myGetMotor_Left_SlaveRAWPOS() {
    	return motor_Left_Slave.getSelectedSensorPosition(0);
    }
    
    public double myGetMotor_Right_SlaveRAWPOS() {
    	return motor_Right_Master.getSelectedSensorPosition(0);
    }
}

