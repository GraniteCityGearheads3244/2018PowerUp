// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.SuperSirAntsABot2.subsystems;

import org.usfirst.frc3244.SuperSirAntsABot2.RobotMap;
import org.usfirst.frc3244.SuperSirAntsABot2.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Intake extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
	public static final double MOTORRPM = 16000;
	public static final double GEARBOX = 20;
	public static final double MINRPM = -400;
	public static final double MAXRPM = 400;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController motor_Right = RobotMap.intakeMotor_Right;
    private final SpeedController motor_Left = RobotMap.intakeMotor_Left;
    private final DoubleSolenoid double_Solenoid_Open = RobotMap.intakeDouble_Solenoid_Open;
    private final DoubleSolenoid double_Solenoid_Close = RobotMap.intakeDouble_Solenoid_Close;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

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

    public void my_intake(double rpm) {
    	rpm = clamp(rpm);
    	double speed = normalize_value(rpm);   	
		motor_Right.set(speed );
		motor_Left.set(speed);
    	
    }
    
    public void my_intake(double rpm, double offset) {
    	rpm = clamp(rpm);
    	double rpm2 = clamp(rpm-offset);
    	double speed = normalize_value(rpm);
    	double speed2 = normalize_value(rpm2); 
		motor_Right.set(speed );
		motor_Left.set(speed2);
    	
    }
    
    public void my_rollers_open(boolean open) {
    	if (open) {
    		double_Solenoid_Open.set(Value.kForward);
    		double_Solenoid_Close.set(Value.kForward);
    	}
    	else {
    		double_Solenoid_Open.set(Value.kReverse);
    		double_Solenoid_Close.set(Value.kReverse);
    	}
    } 	
    public void my_rollers_float() {
    	double_Solenoid_Open.set(Value.kReverse);
		double_Solenoid_Close.set(Value.kForward);
		
	}
    public void my_rollers_off() {
    	double_Solenoid_Open.set(Value.kOff);
		double_Solenoid_Close.set(Value.kOff);
		
	}
    
    private double normalize_value(double value) {
		return (value * GEARBOX)/MOTORRPM;
    	
    }
    private double clamp(double rpm){
    	if(rpm > MAXRPM) {
    		return MAXRPM;
    	}
    	else if(rpm < MINRPM){
    		return MINRPM;
    	}
    	else {
    		return rpm;
    	}
		
    	
    }
    public void updateSmartDashboard() {
    	
    }

	
    
}
