package frc.robot;

import javax.xml.stream.events.Comment;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.robot.commands.*;
import frc.robot.robot.subsystems.swerve.rev.RevSwerve;

import frc.robot.robot.subsystems.arm.ArmSubsystem;
import frc.robot.robot.subsystems.arm.ShootSubsystem;
import frc.robot.robot.subsystems.arm.IntakeSubsystem;

import edu.wpi.first.wpilibj.Timer;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    private static final int LEFT_BUTTON_NUMBER = 5; // Update with the correct button number
    private static final int RIGHT_BUTTON_NUMBER = 6; // Update with the correct button number
    private static final double ARM_SPEED = 0.25; // Adjust arm speed as needed

    /* Controllers */
    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;
    
    private final int rightJoystickX = XboxController.Axis.kRightTrigger.value;
    private final int leftJoystickX = XboxController.Axis.kLeftTrigger.value;

    /* Driver Buttons */
    // private final Trigger zeroGyro = driver.y();
    // private final Trigger intakeReverse = operator.y();

    /* Subsystems */
    private final RevSwerve s_Swerve = new RevSwerve();
    private final ArmSubsystem s_Arm = new ArmSubsystem();
    private final ShootSubsystem s_Shoot = new ShootSubsystem();
    private final IntakeSubsystem s_Intake = new IntakeSubsystem();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */


    public RobotContainer() {
        Arm armCommand = new Arm(
                s_Arm,
                () -> operator.leftBumper().getAsBoolean()  ? ARM_SPEED
                        : operator.rightBumper().getAsBoolean() ? -(ARM_SPEED * 2) : 0);

        s_Arm.setDefaultCommand(armCommand);

        MoveArmCommand armEncoderCommand = new MoveArmCommand(s_Arm, () -> 0.11899);

        operator.b().whileTrue(Commands.run(() -> s_Arm.moveArmWithEncoder(0, 0.11899), s_Arm));//armEncoderCommand);
        //operator.y().whileTrue(Commands.run(() -> s_Arm.moveArmWithEncoder(0, 0.11899), s_Arm));

        Intake intakeCommand = new Intake(
                s_Intake,
                () -> operator.getRawAxis(leftJoystickX));

        s_Intake.setDefaultCommand(intakeCommand);

        Shoot shootCommand = new Shoot(
                s_Shoot,
                () -> operator.getRawAxis(rightJoystickX));

        s_Shoot.setDefaultCommand(shootCommand);
        
        s_Swerve.setDefaultCommand(
                new TeleopSwerve(
                        s_Swerve,
                        () -> driver.getRawAxis(translationAxis),
                        () -> driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis),
                        () -> false));

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        driver.y().onTrue(new InstantCommand(() -> s_Swerve.zeroGyro())); // Reset Gyro

        operator.a().whileTrue( // Reverse Intake
            new Intake(
                s_Intake,
                () -> -0.3
            )
        );
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand(Timer timer) {
        return new Auton(s_Swerve, s_Arm, s_Intake, s_Shoot, timer, s_Arm);
    }
}
