package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LaunchPad_Output_Control extends Command {

    private int m_outputNumber;
	private boolean m_value;

	public LaunchPad_Output_Control(int ouputNumber, boolean value) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		m_outputNumber = ouputNumber;
		m_value = value;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.oi.launchPad.setOutput(m_outputNumber, m_value);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
