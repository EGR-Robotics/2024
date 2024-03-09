package frc.robot.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.robot.subsystems.arm.IntakeSubsystem;

public class Intake extends Command {
    private IntakeSubsystem intakeSubsystem;
    private DoubleSupplier speedSup;

    public Intake(IntakeSubsystem subsystem, DoubleSupplier speedSup) {
        this.intakeSubsystem = subsystem;
        this.speedSup = speedSup;

        addRequirements(intakeSubsystem);
    }

    @Override
    public void execute() {
        intakeSubsystem.intake(speedSup.getAsDouble());
    }
}
