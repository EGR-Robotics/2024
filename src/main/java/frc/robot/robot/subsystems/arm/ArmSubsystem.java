package frc.robot.robot.subsystems.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DutyCycle;

public class ArmSubsystem extends SubsystemBase {
    private final TalonSRX leftArmMotor;
    private final TalonSRX rightArmMotor;

    private final TalonSRX leftFly;
    private final TalonSRX rightFly;
    private final TalonSRX intake;
    // private final CANEncoder armEncoder;
    private final DigitalInput armEncoderDIO;
    private final DutyCycle armEncoder;

    public ArmSubsystem() {
        this.leftArmMotor = new TalonSRX(17);
        this.rightArmMotor = new TalonSRX(13);

        this.armEncoderDIO = new DigitalInput(9);
        this.armEncoder = new DutyCycle(armEncoderDIO);

        this.leftFly = new TalonSRX(16);
        this.rightFly = new TalonSRX(15);
        this.intake = new TalonSRX(18);
    
    }

    public void moveArm(double speed) {
        leftArmMotor.set(ControlMode.PercentOutput, speed);
        rightArmMotor.set(ControlMode.PercentOutput, -speed);
    }
    public void moveArmWithEncoder(double max_speed, double targetAngle) {
        double encoderVal = armEncoder.getOutput();

        leftArmMotor.set(ControlMode.PercentOutput, (targetAngle - encoderVal) * 5);
        rightArmMotor.set(ControlMode.PercentOutput, -(targetAngle - encoderVal) * 5);
    }

    public double getArmEncoder() {
        return armEncoder.getOutput();
    }

    public void stopArm() {
        leftArmMotor.set(ControlMode.PercentOutput, 0);
        rightArmMotor.set(ControlMode.PercentOutput, 0);
    }

    // public double getArmPosition() {
        // return armEncoder.getPosition();
    // }
}
