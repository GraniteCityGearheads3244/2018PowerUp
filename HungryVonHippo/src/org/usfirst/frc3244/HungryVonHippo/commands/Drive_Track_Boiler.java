package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive_Track_Boiler extends Command {

	private Timer t = new Timer();
	private Timer t2 = new Timer();
	private double m_target;
	double m_rawTargetX;
	double m_AngleTargetX;
	double m_current_heading;
	
	
    public Drive_Track_Boiler() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	t.reset();
    	t.start();
    	t2.reset();
    	t2.start();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(t.get() > 2){//Start Tracking
    		m_target = Robot.vision_hardware.getScaled_Boiler_TargetXpos();
    		SmartDashboard.putNumber("Drive Track Boiler Value", m_target);
    		m_rawTargetX = Robot.vision_hardware.getRaw_Boiler_TargetXpos();
    		SmartDashboard.putNumber("Raw Target Xpos",m_rawTargetX);
    		m_AngleTargetX = Robot.vision_hardware.getAngle_To_Boiler_Target();
    		SmartDashboard.putNumber("Angle to Target", m_AngleTargetX);
    	}
    	if(t2.get() > 2){
    		m_current_heading = Robot.drive.getHeading();
    		//Robot.drive.setdesiredHeading(m_current_heading+m_AngleTargetX);
    		t2.reset();
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
