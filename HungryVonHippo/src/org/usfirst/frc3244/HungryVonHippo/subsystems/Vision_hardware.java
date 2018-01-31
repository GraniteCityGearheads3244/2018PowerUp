// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.HungryVonHippo.subsystems;

import org.usfirst.frc3244.HungryVonHippo.RobotMap;
import org.usfirst.frc3244.HungryVonHippo.commands.*;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class Vision_hardware extends Subsystem {
	
	private static final int AXIS_IMG_WIDTH = 320;
	private static final int AXIS_IMG_HEIGHT = 240;
	private static final int AXIS_FOV = 67;
	private static final int LIFECAM_IMG_WIDTH = 320;
	private static final int LIFECAM_IMG_HEIGHT = 240;
	private static final int LIFECAM_FOV = 67;
	UsbCamera camera_Right;
	AxisCamera camera_Front;
	
	/**
	 * Added following to get vision*********************************************
	 */
	NetworkTable tableBoiler;
	NetworkTable tablePeg;
	int m_networkTableReadErrorCounter = 0;
	
	 /**
     * ***************************************************************************
     */
	
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final Relay lights_Left_and_Forward = RobotMap.vision_hardwarelights_Left_and_Forward;
    private final Relay lights_Right = RobotMap.vision_hardwarelights_Right;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
	 * Added following to get vision**********************************************
	 */
    //create a now constructor
    public Vision_hardware(){
    	//Get the pointer to the networktable myContoursReport
    	tableBoiler = NetworkTable.getTable("GRIP/myBoilerContoursReport");
    	tablePeg = NetworkTable.getTable("GRIP/myPegContoursReport");
    	
    	
    }
    /**
     * ***************************************************************************
     */

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    /**
	 * Added following to get vision*********************************************
	 */
    
    
	public double getScoredRawBoilerTargetXpos(){
		double xPos; // return value
		// 1) Get the current list of targets found. There might be more than one visible at a time
		// Firststep: get the vision system data for the target
		
		double[] defaultValue = new double[0]; // set up a default value in case the table isn't published yet
		
		// Get all needed table items at the same time, in case the table updates between reads.
		// (could end up with different array sizes)
		double[] targetX = tableBoiler.getNumberArray("centerX", defaultValue);
		double[][] alinmentXscore; 
		double largest = 0;
		int largestIndex = 0;
		
		if (targetX.length>1){//We Found Targets to sort
			if (targetX.length<5){
				//How Many Combinations?
				int combinations=0; 
				for (int i =1; i < targetX.length;i++){
					combinations=combinations+i;
				}
				//Score all the pairs
				alinmentXscore = new double[combinations][2];
				int pointer = 0;
				for (int i1 = 0; i1 < targetX.length - 1; i1++){
					for (int i2 = 1; i2< targetX.length - 1; i2++){
						alinmentXscore[pointer][0] = score_Boiler_X_Alilnment(((targetX[i1] - targetX[i2]))+1);
						alinmentXscore[pointer][1] = targetX[i1];
						pointer=pointer+1;
					}
				}
				
				//Now Find the best score
	    		for(int i = 0; i < alinmentXscore.length;i++){
	    			if(alinmentXscore[i][0]>largest){
	    				largest = alinmentXscore[i][0];
	    				largestIndex = i;
	    			}
	    		}
	    		
	    		xPos = alinmentXscore[largestIndex][1];
	    		
			}else{
				//To Many targets
				xPos = AXIS_IMG_WIDTH/2;
				
				DriverStation.reportError("Too Many Targets. Fix Filter", false);
			}
		}else{
			// we didn't find ANY object. Return a perfectly centered answer so that the system doesn't
			// try to adapt
			xPos = AXIS_IMG_WIDTH/2;
		
		}
		return xPos;
	}
	
	private double score_Boiler_X_Alilnment(double val){
    	return 100-(100*Math.abs(1-val));
    }
	
	public double getRaw_Boiler_TargetXpos() {
		double xPos; // return value
		
		// 1) Get the current list of targets found. There might be more than one visible at a time
		// Firststep: get the vision system data for the target
		
		double[] defaultValue = new double[0]; // set up a default value in case the table isn't published yet
		
		// Get all needed table items at the same time, in case the table updates between reads.
		// (could end up with different array sizes)
		double[] targetX = tableBoiler.getNumberArray("centerX", defaultValue);
		double[] areas = tableBoiler.getNumberArray("area", defaultValue);
		
		if (targetX.length != areas.length) {
			
			if (targetX.length==0){
				// we didn't find ANY object. Return a perfectly centered answer so that the system doesn't
				// try to adapt
				xPos = AXIS_IMG_WIDTH/2;
				
			}else{
				xPos = targetX[0];
				
			}
			// we didn't find ANY object. Return a perfectly centered answer so that the system doesn't
			// try to adapt
			xPos = AXIS_IMG_WIDTH/2;
			return xPos;
		}
		// For initial debug, just print out the table so we can see what's going on
		/*
		System.out.print("centerX: ");
		for (double xval : targetX) { // for each target found,
		System.out.print(xval + " ");
		}
		System.out.println();
		*/
		
		// 2) Choose the one that has the largest area. This is PROBABLY the closest target (and most inline)
		// Don't want to choose the one closest to the center because that might actually be the target
		// for a different face that's very oblique to our robot position.
		int largestIdx = 0;
		if (targetX.length > 1) {
			double largest = 0;
			for (int c = 0; c < areas.length; c++) {
				if (areas[c] > largest){
				largest = areas[c];
				largestIdx = c;
				}
			}
		}
		if (targetX.length==0){
			// we didn't find ANY object. Return a perfectly centered answer so that the system doesn't
			// try to adapt
			xPos = AXIS_IMG_WIDTH/2;
		}else{
			xPos = targetX[largestIdx];
		}
		return xPos;
	}
	
    public double getScaled_Boiler_TargetXpos() {
    	// get the raw position
    	double raw = getRaw_Boiler_TargetXpos();
    	// Scale the resolution
    	// Find out how far (magnitude and direction) off-center that target is (the "error")
    	// positions in the NetworkTable are in pixels relative to the camera resolution, with (0,0) being the upper left corner
    	// and (resolutionx,resolutiony) being the bottom right. We would want to convert to a different scale
    	// where 0,0 is center, and the extents are -1 and +1 (like a joystick input!)
    	// The equation for this (see "identifying and processing the targets" under Vision on the Screensteps pages)
    	// Aim = (Pixel - resolution/2)/(resolution/2) for each of x and y direction
    	double scaled = (raw-AXIS_IMG_WIDTH/2)/(AXIS_IMG_WIDTH/2);
    	return scaled;
    }
    public double getAngle_To_Boiler_Target(){
    	double raw = getRaw_Boiler_TargetXpos();
    	return ((raw / AXIS_IMG_WIDTH) * AXIS_FOV) - (AXIS_FOV/2);
    }
    
    //*************************************************  Start PEG        *******************************************************
    
    //*************************************************  End PEG        *******************************************************
   
	 /**
     * ***************************************************************************
     */
    
    public void My_Start_USB_Camera_Right(){
    	camera_Right = CameraServer.getInstance().startAutomaticCapture();
    	camera_Right.setBrightness(10);//Default was 45
    	camera_Right.setFPS(20);
    	//Path found was camera_Right.getPath() = /dev/video0
    	
	    camera_Right.setResolution(AXIS_IMG_WIDTH, AXIS_IMG_HEIGHT);
    }
    
    public void My_Start_IP_Camera(){
    	camera_Front = CameraServer.getInstance().addAxisCamera("10.32.44.11");
    }
    
    public void My_Stop_IP_Camera(){
    	//CameraServer.getInstance().removeCamera(camera_Front.getName());
    }
    
    public void My_Stop_USB_Camera_Right(){
    	//camera_Right.
    }
    
    public void My_All_Lights_Off(){
    	lights_Left_and_Forward.set(Value.kOff);
    	lights_Right.set(Value.kOff);
    }
    
    public void My_Left_Light(boolean on){
    	if(on){
    		lights_Left_and_Forward.set(Value.kReverse);
    	}else{
    		lights_Left_and_Forward.set(Value.kOff);
    	}
    }
    
    public void My_Left_Forward(boolean on){
    	if(on){
    		lights_Left_and_Forward.set(Value.kForward);
    	}else{
    		lights_Left_and_Forward.set(Value.kOff);
    	}
    }
    
    public void My_Right_Light(boolean on){
    	if(on){
    		lights_Right.set(Value.kReverse);
    	}else{
    		lights_Right.set(Value.kOff);
    	}
    }
	
}

