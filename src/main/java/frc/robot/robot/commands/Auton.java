package frc.robot.robot.commands;

import frc.robot.robot.commands.*;

import frc.robot.robot.Constants;
import frc.robot.robot.subsystems.swerve.rev.RevSwerve;
import frc.robot.robot.subsystems.swerve.rev.RevSwerveConfig;
import frc.robot.robot.subsystems.arm.ArmSubsystem;
import frc.robot.robot.subsystems.arm.IntakeSubsystem;
import frc.robot.robot.subsystems.arm.ShootSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;

import java.util.function.DoubleSupplier;

public class Auton extends Command {
    private RevSwerve s_Swerve;
    private ArmSubsystem subsystem;
    private IntakeSubsystem intake;
    private ShootSubsystem shoot;

    private Timer timer;

    public Auton(RevSwerve s_Swerve, ArmSubsystem subsystem, IntakeSubsystem intake, ShootSubsystem shoot, Timer timer) {
        this.s_Swerve = s_Swerve;
        this.subsystem = subsystem;
        this.intake = intake;
        this.shoot = shoot;

        addRequirements(shoot);
        addRequirements(subsystem);
        addRequirements(intake);
        addRequirements(s_Swerve);
    
        this.timer = timer;
    }

    private Translation2d getTranslation(DoubleSupplier translationSup, DoubleSupplier strafeSup) {
        /* Get Values, Deadband*/
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband);

        return new Translation2d(translationVal, strafeVal).times(RevSwerveConfig.maxSpeed);
    }

    private double getRotation(DoubleSupplier rotationSup) {
        return MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.stickDeadband);
    }

    @Override
    public void execute() {
        if(timer.get() < 5) {
             s_Swerve.drive(
                getTranslation(
                    () -> 5,
                    () -> 0
                ),
                getRotation(() -> 0),
                true,
                true 
             );
        }
        else if(timer.get() < 5) {
            intake.intake(0.5);
        }
        else if(timer.get() < 6) {
            intake.stopIntake();
        }
    }
}
