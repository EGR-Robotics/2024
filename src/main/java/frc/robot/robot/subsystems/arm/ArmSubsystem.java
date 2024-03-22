package frc.robot.robot.subsystems.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;

public class ArmSubsystem extends SubsystemBase {
    private final TalonSRX leftArmMotor;
    private final TalonSRX rightArmMotor;

    private final DigitalInput armEncoderDIO;
    private final DutyCycle armEncoder;

    public ArmSubsystem() {
        this.leftArmMotor = new TalonSRX(17);
        this.rightArmMotor = new TalonSRX(13);

        this.armEncoderDIO = new DigitalInput(9);
        this.armEncoder = new DutyCycle(armEncoderDIO);
    }

    public void moveArm(double speed) {
        if(getArmEncoder() < 0.09 && speed < 0) return;
    
        leftArmMotor.set(ControlMode.PercentOutput, speed);
        rightArmMotor.set(ControlMode.PercentOutput, -speed);
    }

    public void moveArm(double speed, double targetAngle) {
        if(targetAngle < 0.09 && speed < 0) return;

        double encoderVal = armEncoder.getOutput();

        leftArmMotor.set(ControlMode.PercentOutput, (targetAngle - encoderVal) * speed);
        rightArmMotor.set(ControlMode.PercentOutput, -(targetAngle - encoderVal) * speed);
    }

    public double getArmEncoder() {
        return armEncoder.getOutput();
    }

    public void stopArm() {
        leftArmMotor.set(ControlMode.PercentOutput, 0);
        rightArmMotor.set(ControlMode.PercentOutput, 0);
    }
}
