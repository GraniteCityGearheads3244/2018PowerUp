package org.usfirst.frc3244.ScissorTest.commands;

import org.usfirst.frc3244.ScissorTest.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Scissor_Simple_Setpoint extends Command {

	private double m_setpoint;
	private double encoderPulsesPerUserUnits = 4096; // 4096 pulses per revolution
	
    public Scissor_Simple_Setpoint(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.scissor);
    	m_setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentLeftEncoder = Robot.scissor.myGetMotor_Left_SlaveRAWPOS() / encoderPulsesPerUserUnits;
    	double currentRightEncoder = Robot.scissor.myGetMotor_Right_SlaveRAWPOS() / encoderPulsesPerUserUnits;
    	double distance_error = m_setpoint - ((currentRightEncoder+currentLeftEncoder)/2);
    	double correction_error = currentRightEncoder-currentLeftEncoder;
    	double speed = speedPID(distance_error);
    	double correction = correctionPID(correction_error);
    	Robot.scissor.myMotorsRun(speed, correction);
    }

    private double correctionPID(double correction_error) {
		double kp = .2;
		double max = .2;
		double min = -.1;
		double output = correction_error * kp;
		if(output > max) {
			return max;
		}else if(output < min) {
			return min;
		}else {
			return output;
		}
	}

	private double speedPID(double distance_error) {
		double kp = .5;
		double max = .8;
		double min = -.3;
		double output = distance_error * kp;
		if(output > max) {
			return max;
		}else if(output < min) {
			return min;
		}else {
			return output;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
