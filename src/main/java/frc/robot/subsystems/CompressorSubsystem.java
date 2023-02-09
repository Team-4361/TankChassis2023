package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Compressor;

public class CompressorSubsystem extends SubsystemBase {
    
    private final WPI_TalonSRX compTalon;
    
    public double motorPower = 1;

    public CompressorSubsystem() {
        this.compTalon = new WPI_TalonSRX(Compressor.COMPRESSOR_ID);
    }
    
    public void activate() {
        compTalon.set(motorPower);
    }

    public void activate(double speed) {
        compTalon.set(speed);
    }

    public void deactivate() {
        compTalon.set(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Motor Power", motorPower);
    }
}
