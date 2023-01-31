package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed;

import static frc.robot.Constants.*;

public class DriveSubsystem extends SubsystemBase {
    private DifferentialDrive diffDrive;

    public DriveSubsystem() {
        WPI_TalonSRX bl = new WPI_TalonSRX(BL_MOTOR_ID);
        WPI_TalonSRX fl = new WPI_TalonSRX(FL_MOTOR_ID);
        WPI_TalonSRX br = new WPI_TalonSRX(BR_MOTOR_ID);
        WPI_TalonSRX fr = new WPI_TalonSRX(FR_MOTOR_ID);
        bl.setInverted(true);
        fl.setInverted(true);
        
        diffDrive = new DifferentialDrive(
            new MotorControllerGroup(fl, bl),
            new MotorControllerGroup(fr, br)
        );
    }

    public void tankDrive(double left, double right) {
        diffDrive.tankDrive(left, right);
    }

    public void arcadeDrive(double x, double rotation) {
        diffDrive.arcadeDrive(x, rotation);
    }
}
