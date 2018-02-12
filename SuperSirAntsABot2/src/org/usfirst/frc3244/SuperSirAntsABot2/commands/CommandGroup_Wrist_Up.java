package org.usfirst.frc3244.SuperSirAntsABot2.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CommandGroup_Wrist_Up extends CommandGroup {

    public CommandGroup_Wrist_Up() {
       addParallel(new Intake_Close(1), 1);
       addParallel(new Wrist_Up(1), 1);
    }
}
