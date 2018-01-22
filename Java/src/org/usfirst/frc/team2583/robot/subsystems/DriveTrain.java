package org.usfirst.frc.team2583.robot.subsystems;

import org.usfirst.frc.team2583.robot.RobotMap;
import org.usfirst.frc.team2583.robot.commands.TankDrive;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private WPI_TalonSRX left1  = new WPI_TalonSRX(RobotMap.leftTalon1);
	private WPI_TalonSRX left2 	= new WPI_TalonSRX(RobotMap.leftTalon2);
	private WPI_TalonSRX left3	= new WPI_TalonSRX(RobotMap.leftTalon3);
	private WPI_TalonSRX right1	= new WPI_TalonSRX(RobotMap.rightTalon1);
	private WPI_TalonSRX right2 = new WPI_TalonSRX(RobotMap.rightTalon2);
	private WPI_TalonSRX right3 = new WPI_TalonSRX(RobotMap.rightTalon3);
	
	private SpeedControllerGroup leftGroup  = new SpeedControllerGroup(left1, left2, left3);
	private SpeedControllerGroup rightGroup = new SpeedControllerGroup(right1, right2, right3);
	
	private DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);
	
	private ADIS16448_IMU imu = new ADIS16448_IMU();
	
    public void initDefaultCommand() {
    	setDefaultCommand(new TankDrive());
    }
    
    public void driveWheels(double left, double right) {
    	drive.tankDrive(left, right);
    }
    
    public void calibrate() {
    	imu.calibrate();
    }
}

