package frc.robot.subsystems.fourbar;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.pid.SparkMaxPIDSubsystem;

import static frc.robot.Constants.FourBarWristValues.*;


public class FourBarWristSubsystem extends SubsystemBase {
    private Encoder encoder;
    public  WPI_TalonSRX motor1;
    public FourBarWristSubsystem(){
        encoder = new Encoder(ENCODER_A, ENCODER_B);
        motor1 = new WPI_TalonSRX(WRIST_MOTOR_ID);
    }
    private double degreesToMotorRotations(double degrees) { return (GEAR_WRIST_RATIO / 360) * degrees; }
    public double getAngle(){
        return (GEAR_WRIST_RATIO/360)*encoder.getDistance();
    }
    public void resetEncoder(){
        encoder.reset();
    }

    public void setSpeed(double speed){
        motor1.set(speed);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("wrist angle",getAngle());
    }
}
