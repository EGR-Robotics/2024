package frc.robot.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import com.revrobotics.CANEncoder;

import frc.robot.robot.subsystems.arm.ArmSubsystem;

public class Shoot extends Command {
    private ArmSubsystem subsystem;

    public Shoot(ArmSubsystem subsystem, DoubleSupplier shootSup) {
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        // Call subsystem.shoot() method
    }

}
