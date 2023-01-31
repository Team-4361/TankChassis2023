package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class DriveToTargetCommand extends CommandBase {
    private final PIDController controller = new PIDController(0.1, 0, 0);

    public DriveToTargetCommand() {
        addRequirements(Robot.drive);
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {
        Robot.drive.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
