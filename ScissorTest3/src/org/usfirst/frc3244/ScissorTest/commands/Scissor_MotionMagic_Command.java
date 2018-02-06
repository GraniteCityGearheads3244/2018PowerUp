package org.usfirst.frc3244.ScissorTest.commands;

import org.usfirst.frc3244.ScissorTest.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Scissor_MotionMagic_Command extends Command {
	
	private double m_setpoint;
    
	public Scissor_MotionMagic_Command(double setpoint) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.scissor);
    	m_setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.scissor.myMotorsMotionMagic(m_setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.scissor.myMotorsSTOP();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
