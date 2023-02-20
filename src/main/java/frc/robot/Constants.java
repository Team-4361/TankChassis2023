// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel;

import frc.robot.util.pid.PresetGroup;
import frc.robot.util.pid.PresetList;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int FL_MOTOR_ID = 14;
    public static final int BL_MOTOR_ID = 3;
    public static final int FR_MOTOR_ID = 8;
    public static final int BR_MOTOR_ID = 6;

    public static class FourBarArmValues {
        public static final int ARM_MOTOR_ID = 2;
        public static final CANSparkMaxLowLevel.MotorType ARM_MOTOR_TYPE = kBrushless;

        // The feed forward values for the arm. These can be automatically calculated by using ReCalc. Having
        // a correct feed forward is important as it compensates for the gravity and resistance that will push
        // the arm down when power is cut.
        public static final double ARM_kS = 0;
        public static final double ARM_kG = 0;
        public static final double ARM_kV = 0;
        public static final double ARM_kA = 0;

        public static final double ARM_kP = 0.01;
        public static final double ARM_kI = 0.00;
        public static final double ARM_kD = 0.00;

        public static final double ARM_GEAR_RATIO = 686; /*:1*/

        public static final PresetList ARM_PRESETS = new PresetList(
                0.0, // fully lowered
                -18.0, // ground
                -176.0, // first hook
                -304.0 // second hook
        );

        public static final PresetGroup FOUR_BAR_PRESETS = new PresetGroup()
            .addPreset("FourBar Arm", ARM_PRESETS)
            .addPreset("FourBar Wrist", FourBarWristValues.WRIST_PRESETS);
    }

    public static class FourBarWristValues {
        public static final int WRIST_MOTOR_ID = 1;
        public static final PresetList WRIST_PRESETS = new PresetList(5.0, 10.0, 15.0, 20.0);
        public static  final double WRIST_GEAR_RATIO = 30;
    }

    public static class FrontCamera {
        public static final String CAMERA_NAME = "photoncamera";
    }

    public static class Compressor {
        public static final int COMPRESSOR_ID = 5;
    }
}
