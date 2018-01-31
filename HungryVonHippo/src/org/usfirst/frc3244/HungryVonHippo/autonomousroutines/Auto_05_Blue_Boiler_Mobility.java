package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;



import org.usfirst.frc3244.HungryVonHippo.commands.CG_FireFuel_WithVision;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_For_Distance;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Turn_To_Setpoint;
import org.usfirst.frc3244.HungryVonHippo.commands.FlyWheel_Run_Delay_Indexer_Use_Flywheel_Pot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_05_Blue_Boiler_Mobility extends CommandGroup {

	//private static final double ROBOT_TO_BLUE_BOILER_ANGLE = 100;
	
	// UNTESTED
	
    public Auto_05_Blue_Boiler_Mobility() {
    	// * not using the Direction Chooser to Buggy addSequential(new Drive_Set_Gyro_Chooser(),1); //Not set
    	addSequential(new Drive_Set_Gyro(-90.0),1); //Facing West
    	//Turn to line up better to Boiler
    	//Don't need to it good at the start addSequential(new  Drive_Turn_To_Setpoint(0.0, 0.0, ROBOT_TO_BLUE_BOILER_ANGLE),3); 
    	//Start unloading
    	
    	//With Vision
    	//addSequential(new CG_FireFuel_WithVision(),10);
    	//With out Vision
    	addSequential(new FlyWheel_Run_Delay_Indexer_Use_Flywheel_Pot(),7);
    	
    	//Get the Mobility Points
    	//Maybe need to do this first ? 
    		//addSequential(new  Drive_Turn_To_Setpoint(0.0, 0.0, 0),3); 
    	//or
    		//addSequential(new  Drive_Turn_To_Setpoint(0.0, 0.4, 0),3); 
    	addSequential(new Drive_For_Distance(0.0, 0.5, 0.0, 17, 0.0),5);
    }
}
