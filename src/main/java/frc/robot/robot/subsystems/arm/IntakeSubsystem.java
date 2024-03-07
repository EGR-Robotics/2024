package frc.robot.robot.subsystems.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private final TalonSRX intake;
    // private final CANEncoder armEncoder;

    public IntakeSubsystem() {
        this.intake = new TalonSRX(18);
    }

    public void intake(double speed) {
        intake.set(ControlMode.PercentOutput, speed);
    }

    public void stopIntake() {
        intake.set(ControlMode.PercentOutput, 0);
    }

    // public double getArmPosition() {
        // return armEncoder.getPosition();
    // }
}
