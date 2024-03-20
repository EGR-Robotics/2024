package frc.robot.robot.commands;

import frc.robot.robot.Constants;
import frc.robot.robot.subsystems.swerve.RevSwerve;
import frc.robot.robot.subsystems.swerve.RevSwerveConfig;
import frc.robot.robot.subsystems.arm.ArmSubsystem;
import frc.robot.robot.subsystems.arm.IntakeSubsystem;
import frc.robot.robot.subsystems.arm.ShootSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.DoubleSupplier;

public class Auton extends Command {
    private RevSwerve swerveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private ShootSubsystem shootSubsystem;
    private ArmSubsystem armSubsystem;

    private int autonMode = 1;

    private Timer timer;

    public Auton(
        int autonMode,
        RevSwerve swerveSubsystem,
        IntakeSubsystem intakeSubsystem,
        ShootSubsystem shootSubsystem,
        ArmSubsystem armSubsystem,
        Timer timer
    ) {
        this.swerveSubsystem = swerveSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.shootSubsystem = shootSubsystem;
        this.armSubsystem = armSubsystem;

        addRequirements(swerveSubsystem);
        addRequirements(intakeSubsystem);
        addRequirements(shootSubsystem);
        addRequirements(armSubsystem);

        this.timer = timer;
        this.autonMode = autonMode;
    }

    private Translation2d getTranslation(DoubleSupplier translationSup, DoubleSupplier strafeSup) {
        /* Get Values, Deadband */
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband);

        return new Translation2d(translationVal, strafeVal).times(RevSwerveConfig.maxSpeed);
    }

    private double getRotation(DoubleSupplier rotationSup) {
        return MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.stickDeadband);
    }

    // Left side of speaker
    private void auton1() {
        if (timer.get() < 2) {
            armSubsystem.moveArm(1, 0.32);
        } 
        else if(timer.get() < 3) {
            shootSubsystem.shoot(1);
        }
        else if(timer.get() < 4) {
            intakeSubsystem.intake(1);
        }
        else if(timer.get() < 5) {
            intakeSubsystem.stopIntake();
            shootSubsystem.stopShoot();
            armSubsystem.stopArm();

            swerveSubsystem.drive(
                getTranslation(
                        () -> -0.5,
                        () -> 0.1),
                getRotation(() -> 0),
                true,
                true
            );
        }
        else if(timer.get() < 7) {
            swerveSubsystem.stopDrive();
        }
    }

    // Center of speaker
    private void auton2() {
        if (timer.get() < 2) {
            armSubsystem.moveArm(5, 0.32);
        } 
        else if(timer.get() < 3) {
            shootSubsystem.shoot(1);
        }
        else if(timer.get() < 4) {
            intakeSubsystem.intake(1);
        }
        else if(timer.get() < 5) {
            shootSubsystem.stopShoot();

            armSubsystem.moveArm(5, 0.37);
            swerveSubsystem.drive(
                getTranslation(
                        () -> -0.5,
                        () -> 0),
                getRotation(() -> 0),
                true,
                true
            );
        }
        else if(timer.get() < 6) {
            intakeSubsystem.stopIntake();

            swerveSubsystem.drive(
                getTranslation(
                        () -> 0.5,
                        () -> 0),
                getRotation(() -> 0),
                true,
                true
            );
        }
        else if(timer.get() < 7.5) {
            armSubsystem.moveArm(5, 0.29);
            swerveSubsystem.stopDrive();
            shootSubsystem.shoot(1);
        }
        else if(timer.get() < 8) {
            intakeSubsystem.intake(1);
        }
        else {
            armSubsystem.stopArm();
            intakeSubsystem.stopIntake();
            shootSubsystem.stopShoot();
        }
    }

    // Right side of speaker
    private void auton3() {
        if (timer.get() < 2) {
            armSubsystem.moveArm(1, 0.32);
        } 
        else if(timer.get() < 3) {
            shootSubsystem.shoot(1);
        }
        else if(timer.get() < 4) {
            intakeSubsystem.intake(1);
        }
        else if(timer.get() < 5) {
            intakeSubsystem.stopIntake();
            shootSubsystem.stopShoot();
            armSubsystem.stopArm();

            swerveSubsystem.drive(
                getTranslation(
                        () -> -0.5,
                        () -> -0.5),
                getRotation(() -> 0),
                true,
                true
            );
        }
        else if(timer.get() < 7) {
            swerveSubsystem.stopDrive();
        }
    }

    @Override
    public void execute() {
        if(autonMode == 1)
            auton2();
        else if(autonMode == 2)
            auton1();
        else
            auton3();
    }
}
