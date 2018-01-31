package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive_Switch_FOD extends Command {
	
	private boolean m_enabled;
	
    public Drive_Switch_FOD(boolean enableFOD) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_enabled = enableFOD;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setFieldOrientedDrive(m_enabled);
    	DriverStation.reportError("DriveSwitchFOD.init()", false);
    	
    	
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
