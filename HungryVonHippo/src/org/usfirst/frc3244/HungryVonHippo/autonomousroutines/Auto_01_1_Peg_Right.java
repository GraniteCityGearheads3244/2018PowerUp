package org.usfirst.frc3244.HungryVonHippo.autonomousroutines;

import org.usfirst.frc3244.HungryVonHippo.Robot;
import org.usfirst.frc3244.HungryVonHippo.commands.CG_Leave_Gear_On_Peg;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_For_Distance;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Turn_To_Setpoint;
import org.usfirst.frc3244.HungryVonHippo.commands.Drive_Set_Gyro;
import org.usfirst.frc3244.HungryVonHippo.commands.Wrist_to_Setpoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_01_1_Peg_Right extends CommandGroup {

	private static final double ROBOT_TO_PEG_ANGLE = -150.0;
    public Auto_01_1_Peg_Right() {
    	//configure Gyro
    	// * not using the Direction Chooser to Buggy addSequential(new Drive_Set_Gyro_Chooser(),1); //Not set
    	addSequential(new Drive_Set_Gyro(180.0),1);
    	//Revere to base line keeping a heading of 180.0deg
    	addSequential(new Drive_For_Distance(0.0, -0.3, 0.0, 18, 179.9));  //Practice Match 6 Was 17.0
    	//Set Wrist to present Gear and spin to pin
    	addParallel(new Wrist_to_Setpoint(Robot.wrist.PRESENT_GEAR_ON_PEG),1);
    	addSequential(new Drive_Turn_To_Setpoint(0.0, 0.0, ROBOT_TO_PEG_ANGLE),2);
    	//Strife to pin
    	addSequential(new Drive_For_Distance(-0.4, 0.0, 0.0, 6.0, 360+ROBOT_TO_PEG_ANGLE),5); //+x is Left 2:36--- before match 3 was 3.5
    	//Leave the gear and move away
    	addSequential(new CG_Leave_Gear_On_Peg(),5);
    }
}
