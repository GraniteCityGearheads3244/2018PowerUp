package org.usfirst.frc3244.HungryVonHippo.commands;

import org.usfirst.frc3244.HungryVonHippo.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climb_Flash_Lights extends Command {

	Timer t = new Timer();
	
    public Climb_Flash_Lights() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	t.reset();
    	t.start();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(t.get()<.25){
    		Robot.vision_hardware.My_Left_Forward(true);
    		Robot.vision_hardware.My_Right_Light(false);
    	}else  if(t.get()<.5){
    		Robot.vision_hardware.My_Left_Light(true);
    		Robot.vision_hardware.My_Right_Light(false);
    	}else  if(t.get()<.75){
    		Robot.vision_hardware.My_Left_Light(false);
    		Robot.vision_hardware.My_Right_Light(true);
    	}else{
    		t.reset();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.vision_hardware.My_All_Lights_Off();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
