package frc.robot.robot.subsystems.swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.lib.util.swerveUtil.CTREModuleState;
import frc.robot.lib.util.swerveUtil.RevSwerveModuleConstants;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.FaultID;

public class RevSwerveModule implements SwerveModule {
    public int moduleNumber;
    private Rotation2d rotOffset;

    private CANSparkMax mAngleMotor;
    private CANSparkMax mDriveMotor;

    private CANcoder angleEncoder;
    private RelativeEncoder relAngleEncoder;
    private RelativeEncoder relDriveEncoder;
    private SwerveModuleState m_desiredState;

    public RevSwerveModule(int moduleNumber, RevSwerveModuleConstants moduleConstants) {
        this.moduleNumber = moduleNumber;
        this.rotOffset = moduleConstants.rotOffset;

        /* Angle Motor Config */
        mAngleMotor = new CANSparkMax(moduleConstants.angleMotorID, MotorType.kBrushless);
        configAngleMotor();

        /* Drive Motor Config */
        mDriveMotor = new CANSparkMax(moduleConstants.driveMotorID, MotorType.kBrushless);
        configDriveMotor();

        /* Angle Encoder Config */

        angleEncoder = new CANcoder(moduleConstants.cancoderID);
        configEncoders();
    }

    private void configEncoders() {
        // absolute encoder

        angleEncoder.getConfigurator().apply(new CANcoderConfiguration());
        // angleEncoder.configAllSettings(new RevSwerveConfig().canCoderConfig);

        relDriveEncoder = mDriveMotor.getEncoder();
        relDriveEncoder.setPosition(0);

        relDriveEncoder.setPositionConversionFactor(RevSwerveConfig.driveRevToMeters);
        relDriveEncoder.setVelocityConversionFactor(RevSwerveConfig.driveRpmToMetersPerSecond);

        relAngleEncoder = mAngleMotor.getEncoder();
        relAngleEncoder.setPositionConversionFactor(RevSwerveConfig.DegreesPerTurnRotation);
        // in degrees/sec
        relAngleEncoder.setVelocityConversionFactor(RevSwerveConfig.DegreesPerTurnRotation / 60);

        resetToAbsolute();
        mDriveMotor.burnFlash();
        mAngleMotor.burnFlash();

    }

    private void configAngleMotor() {
        mAngleMotor.restoreFactoryDefaults();
        SparkPIDController controller = mAngleMotor.getPIDController();

        controller.setP(RevSwerveConfig.angleKP, 0);
        controller.setI(RevSwerveConfig.angleKI, 0);
        controller.setD(RevSwerveConfig.angleKD, 0);
        controller.setFF(RevSwerveConfig.angleKF, 0);

        controller.setOutputRange(-RevSwerveConfig.anglePower, RevSwerveConfig.anglePower);
        mAngleMotor.setSmartCurrentLimit(RevSwerveConfig.angleContinuousCurrentLimit);

        mAngleMotor.setInverted(RevSwerveConfig.angleMotorInvert);
        mAngleMotor.setIdleMode(RevSwerveConfig.angleIdleMode);
    }

    private void configDriveMotor() {
        mDriveMotor.restoreFactoryDefaults();
        SparkPIDController controller = mDriveMotor.getPIDController();

        controller.setP(RevSwerveConfig.driveKP, 0);
        controller.setI(RevSwerveConfig.driveKI, 0);
        controller.setD(RevSwerveConfig.driveKD, 0);
        controller.setFF(RevSwerveConfig.driveKF, 0);

        controller.setOutputRange(-RevSwerveConfig.drivePower, RevSwerveConfig.drivePower);

        mDriveMotor.setSmartCurrentLimit(RevSwerveConfig.driveContinuousCurrentLimit);
        mDriveMotor.setInverted(RevSwerveConfig.driveMotorInvert);
        mDriveMotor.setIdleMode(RevSwerveConfig.driveIdleMode);
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        /*
         * This is a custom optimize function, since default WPILib optimize assumes
         * continuous controller which CTRE and Rev onboard is not
         */

        // CTREModuleState actually works for any type of motor.
        desiredState = CTREModuleState.optimize(desiredState, getState().angle);
        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);

        if (mDriveMotor.getFault(FaultID.kSensorFault))
            DriverStation.reportWarning("Sensor Fault on Drive Motor ID:" + mDriveMotor.getDeviceId(), false);

        if (mAngleMotor.getFault(FaultID.kSensorFault))
            DriverStation.reportWarning("Sensor Fault on Angle Motor ID:" + mAngleMotor.getDeviceId(), false);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            double percentOutput = desiredState.speedMetersPerSecond / RevSwerveConfig.maxSpeed;
            mDriveMotor.set(percentOutput);

            return;
        }

        double velocity = desiredState.speedMetersPerSecond;

        SparkPIDController controller = mDriveMotor.getPIDController();
        controller.setReference(velocity, ControlType.kVelocity, 0);
    }

    private void setAngle(SwerveModuleState desiredState) {
        if (Math.abs(desiredState.speedMetersPerSecond) <= (RevSwerveConfig.maxSpeed * 0.01)) {
            mAngleMotor.stopMotor();
            
            return;
        }

        Rotation2d angle = desiredState.angle;
        // Prevent rotating module if speed is less then 1%. Prevents Jittering.

        SparkPIDController controller = mAngleMotor.getPIDController();

        double degReference = angle.getDegrees();

        controller.setReference(degReference, ControlType.kPosition, 0);
    }

    public SwerveModuleState getDesiredState() {
        return m_desiredState;
    }

    public Rotation2d getAngle() {
        return Rotation2d.fromDegrees(relAngleEncoder.getPosition());
    }

    public Rotation2d getDesiredAngle() {
        return getDesiredState().angle;
    }

    public double getDesiredVelocity() {
        return getDesiredState().speedMetersPerSecond;
    }

    public Rotation2d getCANcoder() {
        return Rotation2d.fromRotations(angleEncoder.getAbsolutePosition().getValue());
    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(int moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    private void resetToAbsolute() {
        double absolutePosition = getCANcoder().getDegrees() - rotOffset.getDegrees();
        relAngleEncoder.setPosition(absolutePosition);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            relDriveEncoder.getVelocity(),
            getAngle()
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            relDriveEncoder.getPosition(),
            getAngle()
        );
    }
}