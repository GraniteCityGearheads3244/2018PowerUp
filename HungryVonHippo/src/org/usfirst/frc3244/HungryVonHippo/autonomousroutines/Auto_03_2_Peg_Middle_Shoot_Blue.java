package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.commands.CG_FireFuel_WithVision;
import org.usfirst.frc3244.HungryVonHippo.commands.DeliverFuel_ConditionalCommand;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_For_Distance;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Turn_To_Setpoint;

import org.usfirst.frc3244.HungryVonHippo.commands.FlyWheel_Run_ToSetPoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Blue Alliance Hopper Autonomous Sequence continued from the Place Left Gear
 */
public class Auto_03_2_Peg_Middle_Shoot_Blue extends CommandGroup {

	private static final double ROBOT_TO_BLUE_BOILER_ANGLE = -90.0;
	
    public Auto_03_2_Peg_Middle_Shoot_Blue() {
    	//Run Peg Left routine
    	addSequential(new Auto_03_1_Peg_Middle()); //Not set
    	//Spin to Boiler
    	//Not Required
    	//Drive forward and square up to Driverstation Wall
    	addSequential(new Drive_For_Distance(0.6, 0.6, 0.0, 15, ROBOT_TO_BLUE_BOILER_ANGLE),5); //+x is Left);
    	addSequential(new Drive_For_Distance(0.4, 0.0, 0.0, 10, ROBOT_TO_BLUE_BOILER_ANGLE),2); //+x is Left);
    	//Find Target
    	// TO DO
    	//Deliver Fuel for the remainder of the match
    	addSequential(new DeliverFuel_ConditionalCommand(),15);
    	//addSequential(new CG_FireFuel_WithVision(),15);
    	//addSequential(new FlyWheel_Run_ToSetPoint(83.0),15); //FlyWheel_Run_ToSetPoint.PIN_LEFT_FUEL_POWER  =75
    	//addSequential(new FlyWheel_Run_ToPot(),15);
    }
}
