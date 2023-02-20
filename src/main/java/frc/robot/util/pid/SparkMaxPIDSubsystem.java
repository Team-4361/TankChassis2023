package frc.robot.util.pid;

import com.revrobotics.*;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.math.GearRatio;

import java.util.function.Supplier;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static edu.wpi.first.math.MathUtil.clamp;

/**
 * This {@link SparkMaxPIDSubsystem} is intended to make {@link CANSparkMax} PID control easier to
 * implement. It automatically takes care of setting target rotations, encoders, zeroing, etc. This PID system
 * is also designed to be operated manually using the <code>translateMotor</code> method.
 *
 * @author Eric Gold (ericg2)
 */
public class SparkMaxPIDSubsystem extends SubsystemBase {
    private final CANSparkMax motor;
    private final PIDController controller;
    private final RelativeEncoder encoder;
    private final String name;

    // TODO: Add preset support to all applicable commands.
    private Supplier<Double> presetSupplier;
    private double lastTarget = Double.MAX_VALUE;

    private double targetRotation, maxSpeed, tolerance;

    private boolean teleopMode;

    /**
     * Sets the Target Rotation that the {@link Encoder} should be set to. While teleoperation mode is disabled,
     * the motor will always spin to match the target.
     *
     * @param rotation The amount of rotations to set the Target for.
     * @see #translateMotor(double)
     */
    public void setTarget(double rotation) { this.targetRotation = rotation; }

    public SparkMaxPIDSubsystem setPresetSupplier(Supplier<Double> presets) {
        this.presetSupplier = presets;
        return this;
    }


    /** @return The current {@link Encoder} position of the {@link CANSparkMax} motor. */
    public double getRotation() { return encoder.getPosition(); }

    public Supplier<Double> getPresetSupplier() { return presetSupplier; }

    /** @return The current Target {@link Encoder} position of the {@link CANSparkMax} motor. */
    public double getTargetRotation() { return targetRotation; }

    /**
     * Manually translates the motor using a given <code>speed</code>. While <code>speed</code> is not zero, the
     * PID control is disabled, allowing manual rotation to occur. The Target Rotation is set to the current {@link Encoder}
     * reading during non-zero operation.
     *
     * @param speed A motor speed from -1.0 to +1.0 to spin the motor.
     */
    public void translateMotor(double speed) {
        if (speed == 0 && teleopMode) {
            // Set the target angle to the current rotations to freeze the value and prevent the PIDController from
            // automatically adjusting to the previous value.
            setTarget(getRotation());
            teleopMode = false;
        }
        if (speed != 0 && !teleopMode)
            teleopMode = true;

        motor.set(speed);
    }

    /**
     * Sets the Tolerance for the {@link PIDController}. This will prevent any encoder inaccuracies from stalling
     * the motor when the target is reached.
     *
     * @param rotations The amount of <b>rotations</b> for Tolerance.
     * @return {@link SparkMaxPIDSubsystem}
     */
    public SparkMaxPIDSubsystem setTolerance(double rotations) {
        this.tolerance = rotations;
        return this;
    }

    /**
     * Resets the {@link Encoder} used for measuring position.
     */
    public void resetEncoder() {
        encoder.setPosition(0);
        targetRotation = 0;
    }

    /**
     * Sets the Maximum Speed for the {@link PIDController}. This will prevent the system from operating more than
     * plus/minus the specified <code>speed</code>
     *
     * @param speed The <code>speed</code> from -1.0 to +1.0 to use.
     * @return {@link SparkMaxPIDSubsystem}
     */
    public SparkMaxPIDSubsystem setMaxSpeed(double speed) {
        this.maxSpeed = speed;
        return this;
    }

    /* The tolerance used for the {@link PIDController}. */
    public double getTolerance() {
        return tolerance;
    }

    /** @return The maximum speed of the {@link PIDController}. */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    public SparkMaxPIDSubsystem setPID(double p, double i, double d) {
        controller.setPID(p, i, d);
        return this;
    }

    /**
     * Checks if the actual value is within a specified tolerance of the expected value
     * @param expected The value to be expected.
     * @param actual The actual value.
     * @param tolerance The maximum error or tolerance that the value can be offset to still be true.
     * @return True/false depending on tolerance.
     */
    private static boolean inTolerance(double expected, double actual, double tolerance) {
        return Math.abs(expected - actual) <= tolerance;
    }

    /**
     * @return If the {@link Encoder} is within the tolerance of the Target. Useful for conditions
     * depending on the Motor being moved to a certain position before proceeding.
     */
    public boolean atTarget() {
        return inTolerance(getRotation(), getTargetRotation(), tolerance);
    }


    public SparkMaxPIDSubsystem(String name, CANSparkMax motor, double kP, double kI, double kD) {
        this.controller = new PIDController(kP, kI, kD);

        this.motor = motor;
        this.name = name;
        this.teleopMode = false;
        this.maxSpeed = 1;
        this.tolerance = 0.5;

        if (motor.getMotorType() == kBrushed) {
            encoder = motor.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 8192);
        } else {
            encoder = motor.getEncoder();
        }

        this.targetRotation = encoder.getPosition();

        controller.setP(kP);
        controller.setI(kI);
        controller.setD(kD);

        motor.enableVoltageCompensation(12);
    }

    public SparkMaxPIDSubsystem(String name, int motorID, double kP, double kI, double kD) {
        this(name, new CANSparkMax(motorID, kBrushless), kP, kI, kD);
    }

    public SparkMaxPIDSubsystem(String name, int motorID) {
        this(name, new CANSparkMax(motorID, kBrushless), 0.01, 0, 0);
    }

    public Command resetEncoderCommand() { return this.runOnce(this::resetEncoder); }

    @Override
    public void periodic() {
        if (lastTarget == Double.MAX_VALUE) {
            lastTarget = presetSupplier.get();
        }

        if (!teleopMode && !atTarget())
            motor.set(clamp(controller.calculate(getRotation(), getTargetRotation()), -maxSpeed, maxSpeed));

        SmartDashboard.putNumber(name + " Rotation", getRotation());
        SmartDashboard.putNumber(name + " Target Rotation", getTargetRotation());
        SmartDashboard.putBoolean(name + " At Target", atTarget());

        double suppliedTarget = presetSupplier.get();
        if (lastTarget != presetSupplier.get()) {
            setTarget(suppliedTarget);
            lastTarget = suppliedTarget;
        }
    }
}
