package frc.robot.lists;

// Imports
import java.util.Map;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.robot.utils.ModuleMap;


public final class Constants {

    /** Constants used by the limelight */
    public static final class LimelightConstants {
        // LIMELIGHT DATA IS OUT OF DATE

        // Limelight calculation numbers
        public static final int LIME_HEIGHT = 39; // Height of limelight off the ground in inches
        public static final int TARGET_HEIGHT = 104; // Height of target in inches
        public static final double LIME_ANGLE = Math.toRadians(51); // Angle of limelight relative to the floor

        // Limelight tuning numbers
        public static final double DESIRED_TARGET_AREA = 1.5;  // Area of the target when the robot reaches the wall
        public static final double DRIVE_K = 0.36;             // How fast to drive toward the target
        public static final double STEER_K = 0.05;             // How quickly the robot turns toward the target
        public static final double MAX_DRIVE = 0.5;            // Simple speed limit so we don't drive too fast
    }

    /** Turning a module to its offset will point it forward */
    public static final class ModuleOffsets {
        public static final double FL_OFFSET = 120;
        public static final double FR_OFFSET = 188;
        public static final double RL_OFFSET = 253;
        public static final double RR_OFFSET = 135;
    }
















    public static final class CanConstants {

        public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 40;
        public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 30;
        public static final int FRONT_LEFT_MODULE_STEER_CANCODER = 2;
        

        public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 41;
        public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 31;
        public static final int FRONT_RIGHT_MODULE_STEER_CANCODER = 4;
        

        public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 43;
        public static final int BACK_LEFT_MODULE_STEER_MOTOR = 33;
        public static final int BACK_LEFT_MODULE_STEER_CANCODER = 3;
        

        public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 42;
        public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 32;
        public static final int BACK_RIGHT_MODULE_STEER_CANCODER = 1;
        

    }

    public static final class DriveConstants {

        public static final boolean kFrontLeftTurningMotorReversed = true;
        public static final boolean kBackLeftTurningMotorReversed = true;
        public static final boolean kFrontRightTurningMotorReversed = true;
        public static final boolean kBackRightTurningMotorReversed = true;

        public static final boolean kFrontLeftDriveMotorReversed = false;
        public static final boolean kBackLeftDriveMotorReversed = false;
        public static final boolean kFrontRightDriveMotorReversed = true;
        public static final boolean kBackRightDriveMotorReversed = true;

        public static final double kTrackWidth = Units.inchesToMeters(22);
        // Distance between centers of right and left wheels on robot
        public static final double kWheelBase = Units.inchesToMeters(27);

        public enum ModulePosition {
            FRONT_LEFT,
            FRONT_RIGHT,
            REAR_LEFT,
            REAR_RIGHT
        }

        public static final Map<ModulePosition, Translation2d> kModuleTranslations = Map.of(
            ModulePosition.FRONT_LEFT, new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            ModulePosition.FRONT_RIGHT, new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            ModulePosition.REAR_LEFT, new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
            ModulePosition.REAR_RIGHT, new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

        public static final SwerveDriveKinematics kSwerveKinematics = new SwerveDriveKinematics(
            ModuleMap.orderedValues(kModuleTranslations, new Translation2d[0]));

        public static final boolean kGyroReversed = true;

        // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
        // These characterization values MUST be determined either experimentally or
        // theoretically
        // for *your* robot's drive.
        // The SysId tool provides a convenient method for obtaining these values for
        // your robot.
        public static final double ksVolts = 1;
        public static final double kvVoltSecondsPerMeter = 0.8;
        public static final double kaVoltSecondsSquaredPerMeter = 0.15;

        public static final double kMaxSpeedMetersPerSecond = 3;

        public static final double kMaxRotationRadiansPerSecond = Math.PI;
        public static final double kMaxRotationRadiansPerSecondSquared = Math.PI;

        public static final double kP_X = 0.2;
        public static final double kD_X = 0;
        public static final double kP_Y = 0.2;
        public static final double kD_Y = 0;
        public static final double kP_Theta = 8;
        public static final double kD_Theta = 0;
        public static double kTranslationSlew = 4;
        public static double kRotationSlew = 6;
        public static double kControllerDeadband = .05;
        public static double kControllerRotDeadband = .1;
        
        public static double kVoltCompensation=12.6;

        // public static final double kMaxRotationRadiansPerSecond =
        // Math.hypot(DriveConstants.kTrackWidth / 2.0,
        // DriveConstants.kWheelBase / 2.0);

        // public static final double MAX_ANGULAR_ACCEL_RADIANS_PER_SECOND_SQUARED = 2 *
        // Math.PI;

    }

    public static final class ModuleConstants {

        // ModuleConfiguration MK4I_L1
        public static final double kWheelDiameterMeters = Units.inchesToMeters(4);

        public static double mk4iL1DriveGearRatio = 1 / ((14.0 / 50.0) * (25.0 / 19.0) * (15.0 / 45.0));// 8.14 .122807

        public static double mk4iL2DriveGearRatio = 1 / ((14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0)); // 5090 comment: new ratio

        public static double mk4iL1TurnGearRatio = 1 / ((14.0 / 50.0) * (10.0 / 60.0));// 21.43 1/.046667

        public static final double kDriveMetersPerEncRev =

            (kWheelDiameterMeters * Math.PI) / mk4iL1DriveGearRatio;

        // in 1 minute at 1 rpm encoder drive moves kDriveMetersPerEncRev
        // so in 1 second encoder travels 1/60 revs = kDriveMetersPerEncRev/60
        // so MPS

        public static final double kDriveEncRPMperMPS = kDriveMetersPerEncRev / 60;// 0.000653304296852

        public static double kEncoderRevsPerMeter = 1 / kDriveMetersPerEncRev;// 25.511337897182322

        public static double kFreeMetersPerSecond = 5600 * kDriveEncRPMperMPS;// 3.6
    
        public static final double kTurningDegreesPerEncRev =

            360 / mk4iL1TurnGearRatio;

        // max turn speed = (5400/ 21.43) revs per min 240 revs per min 4250 deg per
        // min
        public static final double kPModuleTurningController = .025;

        public static final double kPModuleDriveController = .2;

        // use sysid on robot
        public static double ksVolts = .055;
        public static double kvVoltSecondsPerMeter = .2;
        public static double kaVoltSecondsSquaredPerMeter = .02;

        public static double kPModuleTurnController;

        public static double kSMmaxAccel = 90;//deg per sec per sec

        public static double maxVel= 90; // deg per sec

        public static double allowedErr = .05;//deg

        // sysid on module?
        public static final double ksDriveVoltSecondsPerMeter = 0.667 / 12;
        public static final double kvDriveVoltSecondsSquaredPerMeter = 2.44 / 12;
        public static final double kaDriveVoltSecondsSquaredPerMeter = 0.27 / 12;
        // sysid on module?
        public static final double kvTurnVoltSecondsPerRadian = 1.47; // originally 1.5
        public static final double kaTurnVoltSecondsSquaredPerRadian = 0.348; // originally 0.3

        
        public static double kMaxModuleAngularSpeedDegPerSec = 90;

        public static final double kMaxModuleAngularAccelerationDegreesPerSecondSquared = 90;

    }

    public static final class OIConstants {
        public static final int kDriverControllerPort = 0;
        public static final int kCoDriverControllerPort = 1;
    
    }

    public static final class TrapezoidConstants {
        
        public static final double kMaxSpeedMetersPerSecond = 3;

        public static final double kMaxAccelerationMetersPerSecondSquared = 3;

        // public static final double kMaxAngularSpeedDegreesPerSecond = 800;

        // public static final double kMaxAngularSpeedDegreesPerSecondSquared =2000;
        public static final double kMaxRotationRadiansPerSecond = Math.PI;
        public static final double kMaxRotationRadiansPerSecondSquared = Math.PI * 2;

        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;

        public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
            kMaxRotationRadiansPerSecond, kMaxRotationRadiansPerSecondSquared);

        // // Constraint for the motion profiled robot angle controller
        // public static final TrapezoidProfile.Constraints kThetaControllerConstraints
        // = new TrapezoidProfile.Constraints(
        // Units.radiansToDegrees(kMaxAngularSpeedRadiansPerSecond),
        // Units.radiansToDegrees(kMaxAngularSpeedRadiansPerSecondSquared));
    }
}
