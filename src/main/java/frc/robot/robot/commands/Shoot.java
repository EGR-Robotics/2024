package frc.robot.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;

import com.revrobotics.CANEncoder;

import frc.robot.robot.subsystems.arm.ShootSubsystem;

public class Shoot extends Command {
    private ShootSubsystem subsystem;
    private DoubleSupplier shootSup;

    public Shoot(ShootSubsystem subsystem, DoubleSupplier shootSup) {
        this.subsystem = subsystem;
        this.shootSup = shootSup;

        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        subsystem.shoot(shootSup.getAsDouble());
    }
}
