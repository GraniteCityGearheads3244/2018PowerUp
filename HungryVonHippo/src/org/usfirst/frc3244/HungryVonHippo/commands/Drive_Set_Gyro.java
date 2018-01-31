package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive_Set_Gyro extends Command {

	private static final double NORTH = 0.0;
	private static final double SOUTH = 180.0;
	private static final double EAST = 90.0;
	private static final double WEST = -90.0;
	
	private double m_angle;
	
    public Drive_Set_Gyro(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setTimeout(.5);
    	m_angle = angle;
    
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	DriverStation.reportWarning("Set Gyro to: " + m_angle, false); 
    	
    	Robot.drive.setgyroOffset(m_angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
   
}
