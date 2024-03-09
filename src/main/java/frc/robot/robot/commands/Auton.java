package frc.robot.robot.commands;

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

import java.util.function.DoubleSupplier;

public class Auton extends Command {
    private RevSwerve swerveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private ShootSubsystem shootSubsystem;
    private ArmSubsystem armSubsystem;

    private Timer timer;

    public Auton(
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

    @Override
    public void execute() {
        if (timer.get() < 2) {
            armSubsystem.moveArm(5, 0.326122);
        } 
        else if(timer.get() < 3) {
            shootSubsystem.shoot(1);
        //    s_Swerve.drive(
        //             getTranslation(
        //                     () -> -0.5,
        //                     () -> -0.5),
        //             getRotation(() -> 0),
        //             true,
        //             true);
        }
        else {
            intakeSubsystem.stopIntake();
            shootSubsystem.stopShoot();
        }
    }
}
