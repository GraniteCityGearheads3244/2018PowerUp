package org.usfirst.frc3244.SuperSirAntsABot.util;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;


/**
 *
 */
public class TalonLimitWatcherButton extends Button {

	private TalonSRX _talon;
	
	
	public TalonLimitWatcherButton(TalonSRX talon){
		_talon = talon;
	
	}
	
	/**
	 * Returns the status of a Normaly Open Switch connected to the RoboRio
	 */
    public boolean get() {
        return _talon.getSensorCollection().isRevLimitSwitchClosed();
    }
}
