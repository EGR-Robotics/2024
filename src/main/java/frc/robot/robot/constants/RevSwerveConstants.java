package frc.robot.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.lib.util.swerveUtil.RevSwerveModuleConstants;

public final class RevSwerveConstants {
    public static final double stickDeadband = 0.1;
    public static final double limelightOffset = 3;

    public static final class REV {
    }

    public static final class Swerve {
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int driveMotorID = 6;
            public static final int angleMotorID = 1;
            public static final int canCoderID = 4;
            public static final Rotation2d rotOffset = Rotation2d.fromRotations(-0.089600);

            public static final RevSwerveModuleConstants constants = new RevSwerveModuleConstants(
                driveMotorID,
                angleMotorID,
                canCoderID,
                rotOffset
            );
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            public static final int driveMotorID = 2;
            public static final int angleMotorID = 8;
            public static final int canCoderID = 3;
            public static final Rotation2d rotOffset = Rotation2d.fromRotations(-0.013184);
            
            public static final RevSwerveModuleConstants constants = new RevSwerveModuleConstants(
                driveMotorID,
                angleMotorID,
                canCoderID,
                rotOffset
            );
        }

        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final int driveMotorID = 10;
            public static final int angleMotorID = 9;
            public static final int canCoderID = 5;
            public static final Rotation2d rotOffset = Rotation2d.fromRotations(0.088867);

            public static final RevSwerveModuleConstants constants = new RevSwerveModuleConstants(
                driveMotorID,
                angleMotorID,
                canCoderID,
                rotOffset    
            );
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final int driveMotorID = 12;
            public static final int angleMotorID = 7;
            public static final int canCoderID = 11;
            public static final Rotation2d rotOffset = Rotation2d.fromRotations(0.169434);

            public static final RevSwerveModuleConstants constants = new RevSwerveModuleConstants(
                driveMotorID,
                angleMotorID,
                canCoderID,
                rotOffset
            );
        }
    }
}