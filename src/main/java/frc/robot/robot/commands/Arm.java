package frc.robot.robot.commands;

import frc.robot.robot.Constants;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import com.revrobotics.CANEncoder;

import frc.robot.robot.subsystems.arm.ArmSubsystem;

public class Arm extends Command {
    private TalonSRX leftArmMotor; // Declare Talon SRX motor object
    private TalonSRX rightArmMotor; // Declare Talon SRX motor object


    private DoubleSupplier armSup;
    private DoubleSupplier flyWheelSup;
    private BooleanSupplier intakeSup;

    private CANEncoder armEncoder; // Declare Encoder object

    private ArmSubsystem subsystem;

    public Arm(ArmSubsystem subsystem, DoubleSupplier armSup, DoubleSupplier flyWheelSup, BooleanSupplier intakeSup) {
        this.leftArmMotor = new TalonSRX(14);
        this.rightArmMotor = new TalonSRX(13);
        
        this.subsystem = subsystem;
        addRequirements(subsystem);

        // Initialize encoder for the arm
        // armEncoder = new CANEncoder();

        // Initialize Limelight - Replace Limelight with your actual Limelight
        // initialization
        // limelight = new Limelight();

        this.armSup = armSup;
        this.flyWheelSup = flyWheelSup;
        this.intakeSup = intakeSup;
        // Store references to other suppliers if needed
    }

    @Override
    public void execute() {
        subsystem.moveArm(armSup.getAsDouble());

        // Get arm encoder position
        // double armPosition = armEncoder.getPosition();

        // Update SmartDashboard with arm encoder value
        // SmartDashboard.putNumber("Arm Position", armPosition);
    }

}
