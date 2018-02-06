package org.usfirst.frc3244.SuperSirAntsABot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc3244.SuperSirAntsABot.Robot;

/**
 *
 */
public class ScissorToSetpoint extends InstantCommand {

	private double m_setpoint;
	
    public ScissorToSetpoint(double height) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.scissor);
    	m_setpoint = height;
    	
    }

    // Called once when this command runs
    @Override
    protected void initialize() {
    	Robot.scissor.my_scissor_height(m_setpoint);
    }

   
}
