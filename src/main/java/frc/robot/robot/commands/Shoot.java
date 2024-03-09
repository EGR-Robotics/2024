package frc.robot.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.robot.subsystems.arm.ShootSubsystem;

public class Shoot extends Command {
    private ShootSubsystem shootSubsystem;
    private DoubleSupplier speedSup;

    public Shoot(ShootSubsystem shootSubsystem, DoubleSupplier speedSup) {
        this.shootSubsystem = shootSubsystem;
        this.speedSup = speedSup;

        addRequirements(shootSubsystem);
    }

    @Override
    public void execute() {
        shootSubsystem.shoot(speedSup.getAsDouble());
    }
}
