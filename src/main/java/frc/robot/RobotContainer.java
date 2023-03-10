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

import static frc.robot.Constants.FourBarArmValues.*;
import static frc.robot.Constants.FourBarArmValues.FOUR_BAR_PRESETS;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button     mappings) should be declared here.
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
            Robot.wrist.translateMotor(xbox.getRightY()/3);
        }, () -> {
            Robot.wrist.translateMotor(0);
        }));

        xbox.povUp().onTrue(Robot.compressor.runOnce(() -> {
            Robot.compressor.motorPower += 0.05;
        }));

        xbox.povDown().onTrue(Robot.compressor.runOnce(() -> {
            Robot.compressor.motorPower -= 0.05;
        }));

        xbox.leftBumper().whileTrue(Commands.runEnd(() -> {
            Robot.compressor.activate();
        }, () -> {
            Robot.compressor.deactivate();
        }));

        xbox.a().onTrue(Commands.runOnce(() -> FOUR_BAR_PRESETS.setCurrentPreset(0)));
        xbox.b().onTrue(Commands.runOnce(() -> FOUR_BAR_PRESETS.setCurrentPreset(1)));
        xbox.y().onTrue(Commands.runOnce(() -> FOUR_BAR_PRESETS.setCurrentPreset(2)));
        xbox.rightBumper().onTrue(Commands.runOnce(() -> FOUR_BAR_PRESETS.setCurrentPreset(3)));

        xbox.leftStick().onTrue(Robot.arm.resetEncoderCommand());
        xbox.x().whileTrue(Commands.runEnd(() -> {
            Robot.arm.translateMotor(xbox.getLeftY());
        }, () -> {
            Robot.arm.translateMotor(xbox.getLeftY());
        }));
    }

}
