package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import static frc.robot.Constants.Climber.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    private final CANSparkMax rotationMotor, extensionMotor;

    public ClimberSubsystem() {
        this.rotationMotor = new CANSparkMax(ROTATION_MOTOR_ID, MotorType.kBrushless);
        this.extensionMotor = new CANSparkMax(EXTENSION_MOTOR_ID, MotorType.kBrushless);
    }

    public void moveRotation(double speed) {
        rotationMotor.set(speed);
    }

    public void moveExtension(double speed) {
        extensionMotor.set(speed);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Rotation Degrees", rotationMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("Extension Degrees", extensionMotor.getEncoder().getPosition());
    }
}
