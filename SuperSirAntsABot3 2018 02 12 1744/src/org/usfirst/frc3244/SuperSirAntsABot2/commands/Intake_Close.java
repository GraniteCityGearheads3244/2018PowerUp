// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.SuperSirAntsABot2.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import org.usfirst.frc3244.SuperSirAntsABot2.Robot;

/**
 *
 */
public class Intake_Close extends TimedCommand {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR

    public Intake_Close(double timeout) {
        super(timeout);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.intake);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	Robot.intake.my_rollers_open(false);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }


    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.intake.my_rollers_off();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }
}
