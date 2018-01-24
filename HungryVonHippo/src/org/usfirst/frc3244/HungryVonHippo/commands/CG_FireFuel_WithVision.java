package org.usfirst.frc3244.HungryVonHippo.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_FireFuel_WithVision extends CommandGroup {

    public CG_FireFuel_WithVision() {
    	addParallel(new Kangaroo_Video_Select_USB(false));
    	addParallel(new Drive_Track_Boiler_PID());
    	//Deliver Fuel 
    	//addSequential(new FlyWheel_Run_ToSetPoint(FlyWheel_Run_ToSetPoint.PIN_LEFT_FUEL_POWER),15);
    	addSequential(new FlyWheel_Run_ToPot());
       
    }
}
