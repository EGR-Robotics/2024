package frc.robot.robot.commands;

import frc.robot.robot.Constants;
import frc.robot.robot.subsystems.swerve.rev.RevSwerve;
import frc.robot.robot.subsystems.swerve.rev.RevSwerveConfig;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;

public class Auton extends Command {
    private RevSwerve s_Swerve;

    public Auton(RevSwerve s_Swerve) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);
    }

    @Override
    public void execute() {
        


        // DoubleSupplier translationSup = () -> 20;
        // DoubleSupplier strafeSup = () -> 0;
        // DoubleSupplier rotationSup = () -> 0;
        // BooleanSupplier robotCentricSup = () -> false;;
        
        // /* Get Values, Deadband*/
        // double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband);
        // double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband);
        // double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.stickDeadband);

        // /* Drive */
        // s_Swerve.drive(
        //     new Translation2d(translationVal, strafeVal).times(RevSwerveConfig.maxSpeed), 
        //     rotationVal * RevSwerveConfig.maxAngularVelocity, 
        //     !robotCentricSup.getAsBoolean(), 
        //     false
        // );
    }
}
