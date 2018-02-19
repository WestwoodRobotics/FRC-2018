/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2583.robot;

import org.usfirst.frc.team2583.robot.commands.UpdateDash;
import org.usfirst.frc.team2583.robot.commands.auto.ForwardLong;
import org.usfirst.frc.team2583.robot.commands.auto.SwitchCL;
import org.usfirst.frc.team2583.robot.commands.auto.SwitchCR;
import org.usfirst.frc.team2583.robot.commands.auto.SwitchLL;
import org.usfirst.frc.team2583.robot.commands.auto.SwitchLR;
import org.usfirst.frc.team2583.robot.commands.auto.SwitchRL;
import org.usfirst.frc.team2583.robot.commands.auto.SwitchRR;
import org.usfirst.frc.team2583.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	private UpdateDash ud = new UpdateDash();
	public static Compressor comp = new Compressor(0);
	
	
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		CameraServer.getInstance().startAutomaticCapture();
		// chooser.addObject("My Auto", new MyAutoCommand());
		comp.setClosedLoopControl(RobotMap.closedLoopControl);
		
		SmartDashboard.putData("Auto mode", m_chooser);
		
		DriveTrain.getInstance().setGear(RobotMap.Gear.LOW); // Start out in low gear
		
		ud.start();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		DriveTrain.getInstance().calibrate();	// Calibrate the imu
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		// Get and store the results of the randomizations of the switches and scales
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.length() == 0) {
			gameData = "LRL";  // in case we don't get it e.g. in testing
		}
		
		if(gameData.length() > 0) {
			FieldMap.homeSwitch = gameData.charAt(0);
			FieldMap.scale = gameData.charAt(1);
			FieldMap.farSwitch = gameData.charAt(2);
		}
		
		m_autonomousCommand = selectAuto();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		
		if (!ud.isRunning()) ud.start();
	}
	
	private Command selectAuto() {
		
		String position = SmartDashboard.getString("StartPos", "Left");
		String strategy = SmartDashboard.getString("Strategy", "Do nothing");
		
		// TODO comment this massive block of horribleness below
		switch(strategy) {
		case RobotMap.strategyBreak:
			if(position.equals(RobotMap.positionC)) {
				return new ForwardLong();	//TODO change this to the appropriate command
			}else {
				return new ForwardLong();
			}
		case RobotMap.strategyAny:	// Pay attention to fall-through
			if(position == RobotMap.positionC) {
				return
						FieldMap.homeSwitch == 'L'
							? new SwitchCL()
							: new SwitchCR();
			}
			else if(position == RobotMap.positionL && FieldMap.homeSwitch == 'R') return new SwitchLR();
			else if(position == RobotMap.positionR && FieldMap.homeSwitch == 'L') return new SwitchRL();
		case RobotMap.strategyOurs:
			return
					position == RobotMap.positionC ? null
				  :	position == RobotMap.positionL ?
						  FieldMap.homeSwitch == 'L' ? new SwitchLL() : new ForwardLong() //TODO might need to change the name from ForwardLong
				  : FieldMap.homeSwitch == 'R' ? new SwitchRR() : new ForwardLong(); //TODO might need to change the name from ForwardLong
		case RobotMap.strategyNothing:
		default:
			return null;
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		DriveTrain.getInstance().resetEncoders();
		DriveTrain.getInstance().setDeadband(RobotMap.deadbandLimit);
		
		if (!ud.isRunning()) ud.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
