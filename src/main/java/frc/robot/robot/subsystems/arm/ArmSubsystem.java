package frc.robot.robot.subsystems.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    private final TalonSRX leftArmMotor;
    private final TalonSRX rightArmMotor;

    private final TalonSRX leftFly;
    private final TalonSRX rightFly;
    private final TalonSRX intake;
    // private final CANEncoder armEncoder;

    public ArmSubsystem() {
        this.leftArmMotor = new TalonSRX(17);
        this.rightArmMotor = new TalonSRX(13);
        // this.armEncoder = armEncoder;

        this.leftFly = new TalonSRX(16);
        this.rightFly = new TalonSRX(15);
        this.intake = new TalonSRX(18);
    }

    public void moveArm(double speed) {
        leftArmMotor.set(ControlMode.PercentOutput, speed);
        rightArmMotor.set(ControlMode.PercentOutput, -speed);
    }

    public void shoot() {
        leftFly.set(ControlMode.PercentOutput, 0.5);
        rightFly.set(ControlMode.PercentOutput, -0.5);
    }

    // public double getArmPosition() {
        // return armEncoder.getPosition();
    // }
}
