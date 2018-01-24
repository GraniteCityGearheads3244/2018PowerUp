package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;
import org.usfirst.frc3244.HungryVonHippo.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive_Track_Boiler_PID extends PIDCommand {

private static final boolean m_debug = false;
	
	private static final double kP = 0.02;
	private static final double kI = 0.0;
	private static final double kD = 0.02;
	
    private boolean rotateToAngle;
	private double rotateToAngleRate;
	static final double kToleranceDegrees = 2.0f;
	
	
	private double m_x;
    private double m_y;
    private double m_currentRotationRate;
    private double m_setpoint;
    Timer m_timer = new Timer();
    Timer m_PIDperiod = new Timer();
    double m_rawTargetX;
	double m_AngleTargetX;
	double m_current_heading;
	
	
    public Drive_Track_Boiler_PID() {
    	super("turnController", kP, kI, kD, 0.02);
        getPIDController().setInputRange(-180.0f,  180.0f);
        getPIDController().setOutputRange(-0.75, 0.75);
        getPIDController().setAbsoluteTolerance(kToleranceDegrees);
        getPIDController().setContinuous(true);
 
        requires(Robot.drive);
    }
    
	@Override
    protected double returnPIDInput() {
        
		return Robot.drive.getHeading();
        //return RobotMap.ahrs.getAngle();
      
    }

	@Override
    protected void usePIDOutput(double output) {
      
    	rotateToAngleRate = output;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(m_debug){
    		System.out.println("DriveTurnToSetpoint Current setpoint: " + m_setpoint);
    	}
    	getPIDController().disable(); // disable PID till timer enables
    	m_setpoint = Robot.drive.getHeading();
    	getPIDController().setSetpoint(m_setpoint);
        rotateToAngle = true;
    	Robot.drive.zeroDistanceTraveled();
    	m_timer.reset();
    	m_timer.start();
    	m_PIDperiod.reset();
    	m_PIDperiod.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//No X,Y values
    	m_y = 0.0;
    	m_x = 0.0;
    	
    	//Wait for LED Ring and Image to settle
    	if(m_timer.get()>1){//Lets start looking for the target
    		getPIDController().enable(); 
    		if (m_PIDperiod.get()>1){ //Lets only update the setpoint every seconds
	    		m_current_heading = Robot.drive.getHeading();
	    		m_AngleTargetX = Robot.vision_hardware.getAngle_To_Boiler_Target();
	    		m_setpoint = m_current_heading+m_AngleTargetX;
	    		if(getPIDController().onTarget()){ //check if still tracking last command
	    			getPIDController().setSetpoint(m_setpoint);
	    		}
	    		m_PIDperiod.reset();
	    		
	    		//set up data
	    		SmartDashboard.putNumber("Angle to Target", m_AngleTargetX);
	    		SmartDashboard.putNumber("Track Boiler PID setpoint", m_setpoint);
    		}
    		
    	}
    	//Use PID Values to spin the Robot
    	//getPIDController().enable();    
    	m_currentRotationRate = rotateToAngleRate;
        Robot.drive.mecanumDriveAutoInTeleop(m_x, m_y, m_currentRotationRate);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //Never finished till fuel delivery is complete
    }

    // Called once after isFinished returns true
    protected void end() {
    	getPIDController().disable();
    	// note:  it is important to call mecanumDriveCartesian here, rather than mecanumDriveAutonomous,
    	// to ensure that "heading preservation" isn't activated for the last instruction
    	Robot.drive.mecanumDriveAutoInTeleopFinished();
    	Robot.drive.mecanumDriveCartesian(0.0, 0.0, 0.0);
    	SmartDashboard.putNumber("Time", m_timer.get());
    	DriverStation.reportWarning("Finished DriveTrackBoilerPID, FlyWheel = "+Robot.flyWheel.My_GetFlyWheelSpeed_Target() + "; Elevator = " + Robot.elevatorServo.getSetpoint(), false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	
	
}
