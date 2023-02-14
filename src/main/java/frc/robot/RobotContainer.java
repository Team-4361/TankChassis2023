// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveToTargetCommand;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    public static final CommandJoystick leftStick = new CommandJoystick(0);
    public static final CommandJoystick rightStick = new CommandJoystick(1);
    public static final CommandXboxController xbox = new CommandXboxController(2);


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }


    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // Add button to command mappings here.
        // See https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
        Robot.drive.setDefaultCommand(Robot.drive.runEnd(() -> {
            Robot.drive.tankDrive(leftStick.getY(), rightStick.getY());
        }, () -> Robot.drive.tankDrive(0,0)));

        Robot.wrist.setDefaultCommand(Robot.wrist.runEnd(() -> {
            Robot.wrist.move(xbox.getRightY()/3);
        }, () -> {
            Robot.wrist.move(0);
        }));

        //xbox.x().whileTrue(new DriveToTargetCommand());

        /* 
        xbox.x().whileTrue(Robot.compressor.runEnd(() -> {
            Robot.compressor.activate();
        }, () -> {
            Robot.compressor.deactivate();
        }));
        */


        xbox.povUp().onTrue(Robot.compressor.runOnce(() -> {
            Robot.compressor.motorPower += 0.05;
        }));

        xbox.povDown().onTrue(Robot.compressor.runOnce(() -> {
            Robot.compressor.motorPower -= 0.05;
        }));


        /* 
        xbox.leftTrigger().whileTrue(Robot.climber.runEnd(() -> {
            Robot.climber.moveExtension(-0.2);
        }, () -> {
            Robot.climber.moveExtension(0);
        }));

        xbox.rightTrigger().whileTrue(Robot.climber.runEnd(() -> {
            Robot.climber.moveExtension(0.2);
        }, () -> {
            Robot.climber.moveExtension(0);
        }));
        */

        /*
        xbox.leftBumper().whileTrue(Robot.climber.runEnd(() -> {
            Robot.climber.moveRotation(-0.2);
        }, () -> {
            Robot.climber.moveRotation(0);
        }));

        xbox.rightBumper().whileTrue(Robot.climber.runEnd(() -> {
            Robot.climber.moveRotation(0.2);
        }, () -> {
            Robot.climber.moveRotation(0);
        }));
         */

        xbox.leftBumper().whileTrue(Commands.runEnd(() -> {
            Robot.compressor.activate();
        }, () -> {
            Robot.compressor.deactivate();
        }));

         SmartDashboard.putNumber("Low Position", 0);
         SmartDashboard.putNumber("Middle Position", 170);
         SmartDashboard.putNumber("High Position", 274);
         SmartDashboard.putNumber("Motor Speed", 1);
         SmartDashboard.putNumber("P", 0.01);
         SmartDashboard.putNumber("I", 0);
         SmartDashboard.putNumber("D", 0);

        xbox.a().whileTrue(Robot.arm.setPresetCommand(0));
        xbox.b().whileTrue(Robot.arm.setPresetCommand(1));
        xbox.y().whileTrue(Robot.arm.setPresetCommand(2));
        xbox.rightBumper().whileTrue(Robot.arm.setPresetCommand(3));

        xbox.leftStick().onTrue(Robot.arm.resetEncoderCommand());
        xbox.x().whileTrue(Commands.runEnd(() -> {
            Robot.arm.translateMotor(xbox.getLeftY());
        }, () -> {
            Robot.arm.translateMotor(xbox.getLeftY());
        }));


    }

}
