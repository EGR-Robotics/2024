package frc.robot.robot.commands;

import java.util.function.DoubleSupplier;

import javax.naming.InsufficientResourcesException;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import com.revrobotics.CANEncoder;

import frc.robot.robot.subsystems.arm.ArmSubsystem;

public class MoveArmCommand extends Command {
    private DoubleSupplier armSup;

    private CANEncoder armEncoder; // Declare Encoder object

    private ArmSubsystem subsystem;

    public MoveArmCommand(ArmSubsystem subsystem, DoubleSupplier armSup) {
        this.subsystem = subsystem;
        addRequirements(subsystem);

        this.armSup = armSup;
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Arm Encoder", subsystem.getArmEncoder());
        subsystem.moveArmWithEncoder(0, armSup.getAsDouble());
    }

}

