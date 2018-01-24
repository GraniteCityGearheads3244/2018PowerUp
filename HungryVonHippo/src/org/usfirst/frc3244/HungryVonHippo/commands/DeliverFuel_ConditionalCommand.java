package org.usfirst.frc3244.HungryVonHippo.commands;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc3244.HungryVonHippo.Robot;
import org.usfirst.frc3244.HungryVonHippo.RobotMap;

/**
 *
 */
public class DeliverFuel_ConditionalCommand extends ConditionalCommand {

	private boolean m_UseVision = true;

  
    public DeliverFuel_ConditionalCommand() {
    	super(new CG_FireFuel_WithVision(), new FlyWheel_Run_ToPot());
    

    }

    protected boolean condition(){
    	return !RobotMap.functionJumpperVision.get();
        //return m_UseVision;
    }
}
