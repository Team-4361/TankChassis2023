package frc.robot.subsystems.fourbar;

import com.revrobotics.CANSparkMax;

import frc.robot.util.math.GearRatio;
import frc.robot.util.pid.SparkMaxAngledPIDSubsystem;
import frc.robot.util.pid.SparkMaxPIDSubsystem;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed;
import static frc.robot.Constants.FourBarArmValues.*;
import static frc.robot.Constants.FourBarWristValues.*;

public class FourBarWristSubsystem extends SparkMaxAngledPIDSubsystem {

    public FourBarWristSubsystem() {
        super(
                "FourBar Wrist",
                new GearRatio(WRIST_GEAR_RATIO),
                new CANSparkMax(WRIST_MOTOR_ID, kBrushed),
                0.01,
                0,
                0
        );
        setTolerance(0.1);
        setPresetSupplier(() -> FOUR_BAR_PRESETS.getCurrentPreset("FourBar Wrist"));
    }
}
