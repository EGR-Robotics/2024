package frc.robot.robot.commands;

import frc.robot.robot.commands.*;

import frc.robot.robot.Constants;
import frc.robot.robot.subsystems.swerve.rev.RevSwerve;
import frc.robot.robot.subsystems.arm.ArmSubsystem;
import frc.robot.robot.subsystems.arm.IntakeSubsystem;
import frc.robot.robot.subsystems.arm.ShootSubsystem;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;

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

    @Override
    public void execute() {
        if(timer.get() < 5) {
            intake.intake(0.5);
        }
        
        if(timer.get() >= 5) {
            intake.stopIntake();
        }
    }
}
