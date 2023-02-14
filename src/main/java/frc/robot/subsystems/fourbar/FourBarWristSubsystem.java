package frc.robot.subsystems.fourbar;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.FourBarWristValues.WRIST_SERVO_ID;

public class FourBarWristSubsystem extends SubsystemBase {

    private WPI_TalonSRX talon = new WPI_TalonSRX(6);
    
    public void move(double power){
        talon.set(power);
    }

}
