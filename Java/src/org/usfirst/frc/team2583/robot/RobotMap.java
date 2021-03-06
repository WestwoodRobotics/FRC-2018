/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2583.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static final double robotWidth = 27,	// The width of the robot (from left to right side)
							   robotLength = 32,	// The length of the robot (from front to back)
							   bumperMod = 6,	// Difference in dimension after bumpers are added
							   totalWidth = robotWidth + bumperMod,
							   totalLength = robotLength + bumperMod;
	
	
	// Encoder, etc. values related to the wheels of the drivetrain
	public static final double encoderPulses = 4096;
	public static final double wheelDiameter = 6; // Diameter in inches
	public static final double pulsesPerInch = encoderPulses / ((wheelDiameter) * 2 * Math.PI);
	public static final int    rightEncA 	 = 0;
	public static final int	   rightEncB	 = 1;
	public static final int    leftEncA		 = 2;
	public static final int    leftEncB		 = 3;
	
	// Potentiometer, etc. values related to the arm
	public static final double armPotMax 	= -1; // The maximum rotation (in degrees) of the potentiometer's range 
	public static final double armMax 		= -1; // The maximum rotation (in degrees) of the robot's arm
	public static final double armMin		= -1; // The minimum rotation (in degrees) of the robot's arm
	public static final double scaleAngle	= -1; // The angle that the arm has to be at to deposit in the scale
	public static final double armPotScale 	= -1; // The change in reading per degree of change in the potentiometer
	public static final double armMaxRead	= armPotScale * armMax;
	public static final double armMinRead 	= armPotScale * armMin;
	
	public static final double armMaxSpeed 	= 0.6;
	
	//Talon motors
	public static final int leftTalon1  = 0;
	public static final int leftTalon2	= 1;
	public static final int leftTalon3 	= 2;
	public static final int rightTalon1 = 3;
	public static final int rightTalon2 = 4;
	public static final int rightTalon3 = 5;
	
	//Arm
	public static final int armTalon = 6;

	//Drivetrain encoders
	public static final int pulsesPerRev = 256 * 9;
	public static final double wheelCircumference = Math.PI * wheelDiameter;
	public static final double distancePerPulse = wheelCircumference / pulsesPerRev;
	
	//Spark motors
	//Intake
	public static final int 
				leftIntakeSpark = 0,
				rightIntakeSpark = 1,
				rampReleaseSpark = 2;
	
	public static final Value
				leftRampDownState  = Value.kReverse,
				rightRampDownState = Value.kReverse,
				leftRampUpState    = leftRampDownState == Value.kReverse ? Value.kForward : Value.kReverse,
				rightRampUpState   = rightRampDownState == Value.kReverse ? Value.kForward : Value.kReverse;
	
	public static final int PDPIndex = 0;
	public static final int PCMIndex = 1;
	
	//Pneumatics control variables
	public static final int 
				armLockChannelA   	= 0,
				armLockChannelB  	= 1,
				clawChannelA  		= 2,
				clawChannelB  		= 3,
				gearShiftChannel  	= 7;
	public static final boolean closedLoopControl = true;
	
	public static final double deadbandLimit = 0.05;
	
	public static final double maxIntakeSpeedL = 0.7;
	public static final double maxIntakeSpeedR = 0.7;
	
	public static final double armSpeedUp = 0.5;
	public static final double armSpeedDown = -0.5;
	
	public static final double driveTimeSpeed = 0.4;	// The drive speed of the time-based auto program
	
	public static final double armDownTime = 0.6;
	
	// These values appear in the DriverStation for human input
	// These are for comparison to the Networktables values
	public static final String
		positionL 		= "Left",
		positionC 		= "Center",
		positionR 		= "Right",
		strategyNothing	= "Do nothing",
		strategyBreak 	= "Break the baseline",
		strategyAny		= "Go for the switch on any side",
		strategyOurs	= "Go for the switch if it is on our side",
		strategyBackward= "Go backward";
	
	// These values appear in the Driverstation for human input
	// These are for the override autonomous capability
	public static final String
		overFL			= "ForwardLong",
		overCL			= "SwitchCL",
		overCR			= "SwitchCR",
		overLL			= "SwitchLL",
		overLR			= "SwitchLR",
		overRL			= "SwitchRL",
		overRR			= "SwitchRR",
		overBreakCL		= "BreakCL",
		overBreakCR		= "BreakCR",
		overTurn		= "TurnTo",
		overNothing		= "Nothing";
	
	/**
	 * Provides constants for directions that things might want to move
	 */
	public enum Dir{
		UP,		// To be used when we need things to move UP
		DOWN,	// To be used when we need things to move DOWN
		NOWHERE;// To be used when we need things to NOT move
	}
	
	public enum Gear{
		HIGH,
		LOW;
	}
	
	public enum Take{
		IN,
		OUT,
		NOWHERE;
	}
}
