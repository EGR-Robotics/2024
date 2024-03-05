package frc.robot.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;

import com.revrobotics.CANEncoder;

import frc.robot.robot.subsystems.arm.IntakeSubsystem;

public class Intake extends Command {
    private IntakeSubsystem subsystem;
    private DoubleSupplier intakeSup;

    public Intake(IntakeSubsystem subsystem, DoubleSupplier intakeSup) {
        this.subsystem = subsystem;
        this.intakeSup = intakeSup;

        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        subsystem.intake(intakeSup.getAsDouble());
    }
}
