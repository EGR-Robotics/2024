package frc.robot.robot.subsystems.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    private final TalonSRX leftArmMotor;
    private final TalonSRX rightArmMotor;
    // private final CANEncoder armEncoder;

    public ArmSubsystem() {
        this.leftArmMotor = new TalonSRX(14);
        this.rightArmMotor = new TalonSRX(13);
        // this.armEncoder = armEncoder;
    }

    public void moveArm(double speed) {
        leftArmMotor.set(ControlMode.PercentOutput, speed);
        rightArmMotor.set(ControlMode.PercentOutput, -speed);
    }

    // public double getArmPosition() {
    //     // return armEncoder.getPosition();
    // }
}
