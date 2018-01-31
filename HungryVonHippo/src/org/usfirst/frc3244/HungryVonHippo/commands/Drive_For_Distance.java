package org.usfirst.frc3244.HungryVonHippo.commands;


import org.usfirst.frc3244.HungryVonHippo.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive_For_Distance extends Command {
	 private double m_x;
	 private double m_y;
	 private double m_rotation;
	 private double m_distance;
	 private double m_heading;
	 private boolean m_CurrentHeading;
	 Timer m_timer = new Timer();
	 
	 /**
	  * Drive For Distance at current robot Heading
	  * Added constructor CPA
	  * @param x
	  * @param y
	  * @param rotation
	  * @param distance
	  */
	 public Drive_For_Distance(double x, double y, double rotation, double distance) {
	    	requires(Robot.drive);
	    	m_x = x;
	    	m_y = y;
	    	m_rotation = rotation;
	    	m_distance = distance;
	    	m_heading = 0;
	    	m_CurrentHeading = true;
	    }
	 
	 /**
	  * Drive For Distance at specified Heading
	  * Original FRC #1519 (Mechanical Mayhem)
	  * @param x
	  * @param y
	  * @param rotation
	  * @param distance
	  * @param heading
	  */
	 public Drive_For_Distance(double x, double y, double rotation, double distance, double heading) {
	    	requires(Robot.drive);
	    	m_x = x;
	    	m_y = y;
	    	m_rotation = rotation;
	    	m_distance = distance;
	    	m_heading = heading;
	    	m_CurrentHeading = false;
	    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.zeroDistanceTraveled();
    	if(m_CurrentHeading){
    		m_heading = Robot.drive.getHeading();
    	}
    	m_timer.reset();
    	m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.mecanumDriveAutonomous(m_x, m_y, m_rotation, m_heading);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.drive.getDistanceTraveled() >= m_distance);
    }

    // Called once after isFinished returns true
    protected void end() {
    	// note:  it is important to call mecanumDriveCartesian here, rather than mecanumDriveAutonomous,
    	// to ensure that "heading preservation" isn't activated for the last instruction
    	Robot.drive.mecanumDriveCartesian(0.0, 0.0, 0.0);
    	SmartDashboard.putNumber("Time", m_timer.get());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	// note:  it is important to call mecanumDriveCartesian here, rather than mecanumDriveAutonomous,
    	// to ensure that "heading preservation" isn't activated for the last instruction
    	Robot.drive.mecanumDriveCartesian(0.0, 0.0, 0.0);
    	SmartDashboard.putNumber("Time", m_timer.get());
    }
}
