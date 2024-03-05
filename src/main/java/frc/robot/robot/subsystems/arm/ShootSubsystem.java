package frc.robot.robot.subsystems.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootSubsystem extends SubsystemBase {
    private final TalonSRX leftFly;
    private final TalonSRX rightFly;

    public ShootSubsystem() {
        this.leftFly = new TalonSRX(16);
        this.rightFly = new TalonSRX(15);
    }

    public void shoot(double speed) {
        leftFly.set(ControlMode.PercentOutput, speed);
        rightFly.set(ControlMode.PercentOutput, speed);
    }
}
