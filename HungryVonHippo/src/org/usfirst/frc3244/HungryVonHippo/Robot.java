// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

/**
 * Restored from save 2017 03 23 1421
 * 
 * Set P factor for Drive
 * Added .5 to all auto pegs after base line / peg offset discovery. Must Test.
 * added disable maintianHeadding
 * Scaled Elevator
 * Scaled Flywheel 70-100%
 * Boiler Tracking OK
 * Peg Tracking Not Ok
 * 
 * Removed Start Angle Chooser WOrking ok
 * 
 * Driver Boiler with vision
 * Co Driver Boiler NO Vision
 *  
 *  04 03 2017 0118
 * Deleting Peg Vision
 * Adding Drive Frozen Diagnostics "Drive;
 * Adding Subsystem Command Ower to SmartDashboard "Robot"
 * added if(DISABLE_SENDABLE_CHOOSER) "Robot"
 * 2017 04 03 1144 untested
 * adding fuel then mobility autonomous Done 1223
 * 
 * 2017 04 06 end of day notes
 * Right Peg boiler change angle for 150 to 130
 * 
 * 2017 5 11
 * changed  reset_xBox_Driver.whenReleased(new CG_Leave_Gear_On_Peg()); to  reset_xBox_Driver.whenPressed(new CG_Leave_Gear_On_Peg());
 * 
 * changed following to use Conditional commands for acquiring targets
 * Auto_01_2_Peg_Right_Shoot_Boiler
 * Auto_02_2_Peg_Left_Shoot_Boiler
 * Auto_03_2_Peg_Middle_Shoot_Blue
 * Auto_03_3_Peg_Middle_Shoot_Red
 * Auto_04_Red_Boiler_Mobility
 * start_xBox_Driver.whileHeld(new ConditionalCommand1()); ********Seems to be working Auto should too !!!!!!!!!!!!
 * 
 * 2017 06 5
 * Added RobotMap.RobotDriveTrainSettings ******* Working
 * 
 * 2017 07 07
 * Added Robot DIO #9 to Select if vision is used or not.
 * 
 * 2017 07 29
 * Change Gear Present from 15 to 18
 */

package org.usfirst.frc3244.HungryVonHippo;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3244.HungryVonHippo.autonomousroutines.*;
import org.usfirst.frc3244.HungryVonHippo.commands.*;
import org.usfirst.frc3244.HungryVonHippo.subsystems.*;
import org.usfirst.frc3244.HungryVonHippo.util.Utils;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static int M_WPI_TalonSRX_config_TimeoutMs = 0;
	
    //public static final boolean DEBUG = true;
    public static final boolean DEBUG = false;
    private static final boolean DISABLE_SENDABLE_CHOOSER = false;
    
    private Runtime runtime = Runtime.getRuntime();
    
    //Disabled variables
    private Integer scancount = 0 ;
	private Integer sequence = 0 ;
	private Integer count = 0;
   
    Command autonomousCommand;
    private String autonomousSelected;
	public static SendableChooser autonomousChooser;
	//public static SendableChooser StartUpChooser;
	private int m_auto = 0;
	

    public static OI oi;
    public static Drive drive;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Winch winch;
    public static FuelIntake fuelIntake;
    public static Wrist wrist;
    public static FlyWheel flyWheel;
    public static Vision_hardware vision_hardware;
    public static ElevatorServo elevatorServo;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public static final PowerDistributionPanel pdp = new PowerDistributionPanel();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	RobotMap.init();
        drive = new Drive(); //Robotbuilder No longer Manages this
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        winch = new Winch();
        fuelIntake = new FuelIntake();
        wrist = new Wrist();
        flyWheel = new FlyWheel();
        vision_hardware = new Vision_hardware();
        elevatorServo = new ElevatorServo();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        
        
        // Initialize the subsystems that need it
        drive.init();
        
        //Set up Choosers
        setupAutomousChooser();
        //setupStartUpChooser();
        //Add a Number Input box to use to manualy select the Autonomous
        SmartDashboard.putNumber("Manualy Selected Autonoums: ", m_auto);
        
        //Zero The Gyro
        //driveTrain.my_zeroHeading(true);
        
        // Start USB Camera
        vision_hardware.My_Start_USB_Camera_Right();
        //vision_hardware.My_Start_IP_Camera();
        //vision_hardware.My_Stop_IP_Camera();
        
        //Add Subsystem Owners to SmartDashboard
        SmartDashboard.putData("DriveTrain", drive);
    }
    
    //This is the back up auto chooser
    // must update the "Drive_Set_Gyro_Chooser" calls to send the gyro choice
    
    private void autoSelect(){
    	m_auto = (int) SmartDashboard.getNumber("Manualy Selected Autonoums: ", m_auto);
    	
    	try{
	    	switch(m_auto){
	    	case 0: 
	    		autonomousCommand = new Auto_00_Reach_BaseLine();
	    		break;
	    	case 1: 
	    		autonomousCommand = new Auto_01_1_Peg_Right();
	    		break;
	    	case 2: 
	    		autonomousCommand = new Auto_01_2_Peg_Right_Shoot_Boiler();
	    		break;
	    	case 3: 
	    		autonomousCommand = new Auto_02_1_Peg_Left();
	    		break;
	    	case 4: 
	    		autonomousCommand = new Auto_02_2_Peg_Left_Shoot_Boiler();
	    		break;
	    	case 5: 
	    		autonomousCommand = new Auto_03_1_Peg_Middle();
	    		break;
	    	case 6: 
	    		autonomousCommand = new Auto_03_2_Peg_Middle_Shoot_Blue();
	    		break;
	    	case 7: 
	    		autonomousCommand = new Auto_03_3_Peg_Middle_Shoot_Red();
	    		break;
	    	case 8: 
	    		autonomousCommand = new Auto_04_Red_Boiler_Mobility();
	    		break;
	    	case 9: 
	    		autonomousCommand = new Auto_05_Blue_Boiler_Mobility();
	    		break;
	    	default:
	    		autonomousCommand = new Auto_00_Reach_BaseLine();
	    		break;
	    	}
    	}catch (Exception e) {
			// TODO: handle exception
    		DriverStation.reportError("Failed to select Autonous, Error= "+ e, false);
		}
    }
    private void setupAutomousChooser(){
    	//Create the Auto Chooser
    	//SmartDashboard.putString("autonomous Title", "Autonomous Choice");
        autonomousChooser = new SendableChooser();
        autonomousChooser.addDefault("0: Reach Base Line - NORTH", new Auto_00_Reach_BaseLine());					//Robot Starts facing NORTH
        autonomousChooser.addObject("1: Peg Right - SOUTH", new Auto_01_1_Peg_Right());								//Robot Starts facing SOUTH
        autonomousChooser.addObject("2: Peg Right Shoot Boiler - SOUTH", new Auto_01_2_Peg_Right_Shoot_Boiler());	//Robot Starts facing SOUTH
        autonomousChooser.addObject("3: Peg Left - NORTH", new Auto_02_1_Peg_Left());								//Robot Starts facing NORTH
        autonomousChooser.addObject("4: Peg Left Shoot Boiler - NORTH", new Auto_02_2_Peg_Left_Shoot_Boiler());		//Robot Starts facing NORTH
        autonomousChooser.addObject("5: Peg Middle - NORTH", new Auto_03_1_Peg_Middle());							//Robot Starts facing NORTH
        autonomousChooser.addObject("6: Peg Middle Shoot Blue - NORTH", new Auto_03_2_Peg_Middle_Shoot_Blue());		//Robot Starts facing NORTH
        autonomousChooser.addObject("7: Peg Middle Shoot Red - NORTH", new Auto_03_3_Peg_Middle_Shoot_Red());		//Robot Starts facing NORTH
        autonomousChooser.addObject("8: Red Boiler then Mobility - EAST", new Auto_04_Red_Boiler_Mobility());		//Robot Starts facing EAST
        autonomousChooser.addObject("9: Blue Boiler then Mobility - WEST", new Auto_05_Blue_Boiler_Mobility());		//Robot Starts facing WEST
        //autonomousChooser.addObject("x: abc", new Auto_06());
        //autonomousChooser.addObject("x: abc", new Auto_07());
        //autonomousChooser.addObject("x: abc", new Auto_08());
        //autonomousChooser.addObject("x: abc", new Auto_09());
        //autonomousChooser.addObject("x: abc", new Auto_10());
        //Add More Options
        
        //Place autonomousChooser on the SmartDashboard
        SmartDashboard.putData("Autonomous Chooser", autonomousChooser);
    }
    
    private void setupStartUpChooser(){
    	//SmartDashboard.putString("goalChooser Title", "Auto Goal Target");
    	/*StartUpChooser = new SendableChooser();
    	StartUpChooser.addDefault("Manual", 0);
    	StartUpChooser.addObject("North 0", 0);
    	StartUpChooser.addObject("South 180", 180);
    	StartUpChooser.addObject("East 90", 90);
    	StartUpChooser.addObject("West -90", -90);
    	SmartDashboard.putData("Sartup Angle Chooser", StartUpChooser);
    	SmartDashboard.putNumber("Start up Angle", 0);
    	*/
    }
    

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	Robot.oi.launchPad.setOutputs(0);
    	//DriverStation.reportError("Drive LoopMode Motor 0=  "+ Robot.drive.getLoopMode(0), false);
    	//Timer.delay(2);
    	//DriverStation.reportError("Drive LoopMode Motor 1=  "+ Robot.drive.getLoopMode(1), false);
    	//Timer.delay(2);
    	//DriverStation.reportError("Drive LoopMode Motor 2=  "+ Robot.drive.getLoopMode(2), false);
    	//Timer.delay(2);
    	//DriverStation.reportError("Drive LoopMode Motor 3=  "+ Robot.drive.getLoopMode(3), false);
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        updateSmartDashboard();
        
        scancount  = scancount+1;
        if (RobotMap.ahrs.isConnected()){
        	Robot.oi.launchPad.setOutputs(sequence);
        }
		
		if (scancount > 10){
        	sequence = sequence<<1;
        	scancount = 0;
        	count =count +1;
        }
        if (count == 11){
        	sequence = sequence+1;
        	count = 0;
        	//Test SmartDashboar Send the current AutoChoice
        	if(DISABLE_SENDABLE_CHOOSER){
        		autoSelect();
        		autonomousSelected = autonomousCommand.getName();
        	}else{
        		autonomousSelected = autonomousChooser.getSelected().toString();
        	}
        	//Put the selected name on the smartdashboard
            SmartDashboard.putString("Auto Choice", autonomousSelected);
        }
        
    }
    
    public void autonomousInit() {
    	//Zero the Gyro
    	Robot.drive.recalibrateHeadingGyro();
    	Robot.drive.set_PreserveHeading(true);// When Testing climb we forget to re-enable
    	
    	DriverStation.reportWarning("Setting Autionomous", false);
    	// get Selected Autonomous
    	if(DISABLE_SENDABLE_CHOOSER){
    		autoSelect();
    		autonomousSelected = autonomousCommand.getName().toString();
    	}else{
    		autonomousCommand = (Command) autonomousChooser.getSelected();
    		autonomousSelected = autonomousChooser.getSelected().toString();
    	}
    	 
    	
    	// What is the name of this Autonomous? Then Send it to the divers.
    	
        SmartDashboard.putString("autonomousInit Auto Choice", autonomousSelected);
        
        DriverStation.reportWarning("Auto Selected @ autonomousInit() = " + autonomousSelected, false);
        
        //Turn Off the LED on the Diverstation Controller.
    	Robot.oi.launchPad.setOutputs(0);
    	
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
        
       
    }


	/**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        updateSmartDashboard();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        DriverStation.reportError("My Entering Teleop.", false);
       
        Robot.drive.clearDesiredHeading();
        Robot.drive.set_PreserveHeading(true);// When Testing climb we forget to re-enable
        
    }

    boolean teleopOnce = false;
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        if (!teleopOnce)
    	{
    	  DriverStation.reportError("My Teleop Periodic is running!", false);
    	}
    	teleopOnce = true;
        Scheduler.getInstance().run();
            
        // update sensors in drive that need periodic update
        //drive.periodic();
//        elevator.periodic();
        
        drive.mecanumDriveTeleop(oi.driveX(), oi.driveY(), oi.driveRotation()); 
        
        updateSmartDashboard();
        
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    private long SMART_DASHBOARD_UPDATE_INTERVAL = 250;
    private long nextSmartDashboardUpdate = System.currentTimeMillis();
    
    public void updateSmartDashboard() {
        try {
            if (System.currentTimeMillis() > nextSmartDashboardUpdate) {
                // display free memory for the JVM
            	//double freeMemoryInKB = runtime.freeMemory() / 1024;
                //SmartDashboard.putNumber("Free Memory", freeMemoryInKB); 
                
                //SmartDashboard.putNumber("Battery Voltage", pdp.getVoltage());
                
                // Interesting Gyro Stuff
                SmartDashboard.putNumber("Gyro Angle", Utils.twoDecimalPlaces(Robot.drive.getHeading()));
                SmartDashboard.putNumber("BCK Gyro Angle", Utils.twoDecimalPlaces(RobotMap.adrxs450_Gyro.getAngle()));
                SmartDashboard.putBoolean(  "IMU_Connected",        RobotMap.ahrs.isConnected());
                SmartDashboard.putBoolean(  "IMU_IsCalibrating",    RobotMap.ahrs.isCalibrating());
            	// display mode information
//                SmartDashboard.putBoolean("Is Teleop", DriverStation.getInstance().isOperatorControl());
//                SmartDashboard.putBoolean("Is Autonomous", DriverStation.getInstance().isAutonomous());
//                SmartDashboard.putBoolean("Is Enabled", DriverStation.getInstance().isEnabled());

            	// display interesting OI information
//                SmartDashboard.putNumber("DriveX", oi.driveX());  
//                SmartDashboard.putNumber("DriveY", oi.driveY());  
//                SmartDashboard.putNumber("DriveRotation", oi.driveRotation());  
                
            	drive.updateSmartDashboard();
            	flyWheel.updateSmartDashboard();
            	elevatorServo.updateSmartDashboard();
            	
            	
 
                nextSmartDashboardUpdate += SMART_DASHBOARD_UPDATE_INTERVAL;
            }
        } catch (Exception e) {
           return;
        }
    }
    
    
}
