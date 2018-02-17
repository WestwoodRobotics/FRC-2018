package org.usfirst.frc.team2583.robot.commands;

import org.usfirst.frc.team2583.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopArm extends Command {
	
	Arm ar = Arm.getInstance();

    public StopArm() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(ar);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ar.stopArm();
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
