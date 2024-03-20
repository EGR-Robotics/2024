package frc.robot;

// Controllers
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

// Commands
import frc.robot.robot.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

// Subsystems
import frc.robot.robot.subsystems.swerve.RevSwerve;
import frc.robot.robot.subsystems.arm.ArmSubsystem;
import frc.robot.robot.subsystems.arm.ShootSubsystem;
import frc.robot.robot.subsystems.arm.IntakeSubsystem;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import edu.wpi.first.wpilibj.Timer;

public class RobotContainer {
    private SendableChooser<Command> autoChooser = new SendableChooser<Command>();

    private static final double ARM_SPEED = 0.25;

    // Controllers
    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);

    // Drive Controls
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    private final int rightJoystick = XboxController.Axis.kRightTrigger.value;
    private final int leftJoystick = XboxController.Axis.kLeftTrigger.value;

    // Subsystems
    private final RevSwerve swerveSubsystem = new RevSwerve();
    private final ArmSubsystem armSubsystem = new ArmSubsystem();
    private final ShootSubsystem shootSubsystem = new ShootSubsystem();
    private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

    public RobotContainer() {
        // Configure commands for PathPlanner auto
        NamedCommands.registerCommand("Shoot", new InstantCommand(() -> shootSubsystem.shoot(1)));
        NamedCommands.registerCommand("Intake", new InstantCommand(() -> intakeSubsystem.intake(1)));
        NamedCommands.registerCommand("Arm To Speaker", new InstantCommand(() -> armSubsystem.moveArm(1, 0.32)));
    
        // Configure autos
        SmartDashboard.putData("Autonomous Mode", autoChooser);

        autoChooser.addOption("Center", new PathPlannerAuto("Center"));

        // Move the arm up and down
        Arm armCommand = new Arm(
            armSubsystem,
            () -> (
                operator.leftBumper().getAsBoolean()
                ? ARM_SPEED
                    : operator.rightBumper().getAsBoolean()
                ? -(ARM_SPEED * 2) 
                    : 0
            )
        );

        armSubsystem.setDefaultCommand(armCommand);

        // Move the arm to the amp
        Arm amp = new Arm(armSubsystem, () -> 5, () -> 0.11899);
        operator.b().whileTrue(amp);

        // Move the arm to the speaker
        Arm speaker = new Arm(armSubsystem, () -> 5, () -> 0.33);
        operator.x().whileTrue(speaker);

        // Intake
        Intake intakeCommand = new Intake(intakeSubsystem, () -> operator.getRawAxis(leftJoystick));
        intakeSubsystem.setDefaultCommand(intakeCommand);

        // Shoot
        Shoot shootCommand = new Shoot(
                shootSubsystem,
                () -> operator.getRawAxis(rightJoystick));
        shootSubsystem.setDefaultCommand(shootCommand);

        // Move
        swerveSubsystem.setDefaultCommand(
            new TeleopSwerve(
                swerveSubsystem,
                () -> driver.getRawAxis(translationAxis),
                () -> driver.getRawAxis(strafeAxis),
                () -> -driver.getRawAxis(rotationAxis),
                () -> false
            )
        );

        configureButtonBindings();

    }

    private void configureButtonBindings() {
        driver.y().onTrue(new InstantCommand(() -> swerveSubsystem.zeroGyro())); // Reset Gyro

        // Reverse Intake
        operator.a().whileTrue(
            new Intake(
                intakeSubsystem,
                () -> -0.3
            )
        );
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();

        // return new Auton(
        //     autonNumber,
        //     swerveSubsystem,
        //     intakeSubsystem,
        //     shootSubsystem,
        //     armSubsystem, 
        //     timer
        // );
    }
}
