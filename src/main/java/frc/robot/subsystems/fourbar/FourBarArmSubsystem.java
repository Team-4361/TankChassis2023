package frc.robot.subsystems.fourbar;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.pid.PresetList;
import frc.robot.util.pid.SparkMaxPIDSubsystem;

import static com.revrobotics.CANSparkMax.ControlType.kPosition;
import static frc.robot.Constants.FourBarArmValues.*;

public class FourBarArmSubsystem extends SparkMaxPIDSubsystem {
    public FourBarArmSubsystem() {
        super(
                "FourBar",
                ARM_MOTOR_ID,
                ARM_kP,
                ARM_kI,
                ARM_kD
        );
        setPresets(ARM_PRESETS);
    }

    @Override
    public void periodic() {
        super.periodic();

        setMaxSpeed(SmartDashboard.getNumber("Motor Speed", 1));
        setPID(
                SmartDashboard.getNumber("P", 0.05),
                SmartDashboard.getNumber("I", 0),
                SmartDashboard.getNumber("D", 0.02)
        );

        PresetList<Double> p = getPresets();

        p.set(0, SmartDashboard.getNumber(DASHBOARD_ZERO, 0));
        p.set(1, SmartDashboard.getNumber(DASHBOARD_ONE, 0));
        p.set(2, SmartDashboard.getNumber(DASHBOARD_TWO, 0));
        p.set(3, SmartDashboard.getNumber(DASHBOARD_THREE, 0));

        setPresets(p);
    }
}
