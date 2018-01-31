package org.usfirst.frc3244.HungryVonHippo.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class Kangaroo_Video_Select_USB extends Command {
	
	NetworkTable table;
	private boolean m_usbSelected;
	
    public Kangaroo_Video_Select_USB(boolean selectUSB) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_usbSelected =selectUSB;
    	setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table = NetworkTable.getTable("GRIP");
    	
    	table.putBoolean("select", m_usbSelected);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
