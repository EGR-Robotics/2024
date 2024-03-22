package frc.robot.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.robot.subsystems.arm.ArmSubsystem;

public class MoveArm extends Command {
    private DoubleSupplier speedSup;
    private DoubleSupplier angleSup;

    private ArmSubsystem armSubsystem;

    public MoveArm(ArmSubsystem subsystem, DoubleSupplier speedSup) {
        this.armSubsystem = subsystem;
        addRequirements(armSubsystem);

        this.speedSup = speedSup;
    }

    public MoveArm(ArmSubsystem subsystem, DoubleSupplier speedSup, DoubleSupplier angleSup) {
        this.armSubsystem = subsystem;
        addRequirements(armSubsystem);

        this.speedSup = speedSup;
        this.angleSup = angleSup;
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Arm Encoder", armSubsystem.getArmEncoder());

        if(angleSup != null)
            armSubsystem.moveArm(speedSup.getAsDouble(), angleSup.getAsDouble());
        else
            armSubsystem.moveArm(speedSup.getAsDouble());
    }
}
