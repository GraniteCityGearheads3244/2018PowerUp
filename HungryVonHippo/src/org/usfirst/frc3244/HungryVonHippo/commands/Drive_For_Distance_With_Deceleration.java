package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;



public class Drive_For_Distance_With_Deceleration extends Command {
	 private double m_x;
	 private double m_y;
	 private double m_rotation;
	 private double m_totalDistance;
	 private double m_heading;
	 private double m_decelerationLength;
	 private double m_fullSpeedLength;

	 private final static int TOLERANCE = 100;
	 Timer m_timer = new Timer();
	 private boolean m_decelerationStarted;
//	 limit the deceleration to 2 seconds
	 private static final double DECELERATION_TIMEOUT = 2.0;
    
	 public Drive_For_Distance_With_Deceleration(double x, double y, double rotation, double totalDistance, 
			 								 double heading, double decelerationLength) {
		 
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		 requires(Robot.drive);
	    	m_x = x;
	    	m_y = y;
	    	m_rotation = rotation;
	    	m_totalDistance = totalDistance;
	    	m_heading = heading;
	    	m_fullSpeedLength = totalDistance - decelerationLength;
	    	m_decelerationLength = decelerationLength;
	    	m_decelerationStarted = false;
	    	
    	 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.drive.zeroDistanceTraveled();
   
    	m_timer.reset();
    	m_timer.start();
    	m_decelerationStarted = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double distanceTraveled = Robot.drive.getDistanceTraveled();
    	double x = m_x;
    	double y = m_y;
    	// if we are still in the full-speed range...
    	if(distanceTraveled < m_fullSpeedLength){
    		// x and y are assumed to be m_x and m_y
    	}else{
    		// we entered the deceleration range
    		
    		// if this is the first time in deceleration...
    		if(!m_decelerationStarted){
    			// dont' restart the timer again
    			m_decelerationStarted = true;
    			
    			// start the timer
    			m_timer.reset();
    			m_timer.start();    			
    		}
    		
    		
    		//now that we are in the decleration phase, scale the x and y speeds
    		//based on how much further we have to go
    		double distanceRemaining = m_totalDistance - distanceTraveled;
    		x = m_x / m_decelerationLength * distanceRemaining;
    		y = m_y / m_decelerationLength * distanceRemaining;
    	}
		Robot.drive.mecanumDriveAutonomous( x, y, m_rotation, m_heading);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return ((Robot.drive.getDistanceTraveled() >= m_totalDistance - TOLERANCE)||
    			(m_timer.get() > DECELERATION_TIMEOUT && m_decelerationStarted));
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.mecanumDriveCartesian(0.0, 0.0, 0.0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.mecanumDriveCartesian(0.0, 0.0, 0.0);
    
    }
}
