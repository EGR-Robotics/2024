package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.lib.util.loggingUtil.LogManager;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    SendableChooser<Command> autoChooser;

    private Command autonomousCommand;

    private RobotContainer robotContainer;

    private Timer timer = new Timer();

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        
        autoChooser = new SendableChooser<Command>();
        autoChooser.addOption("Center Speaker", robotContainer.getAutonomousCommand(timer, 1));
        autoChooser.addOption("Short-Side Speaker", robotContainer.getAutonomousCommand(timer, 2));
        autoChooser.addOption("Long-Side Speaker", robotContainer.getAutonomousCommand(timer, 3));
        SmartDashboard.putData("Auto Choices", autoChooser);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        LogManager.log();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        timer.reset();
        timer.start();

        autonomousCommand = (Command) autoChooser.getSelected();
    }

    @Override
    public void autonomousPeriodic() {
        if (autonomousCommand != null)
            autonomousCommand.schedule();
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null)
            autonomousCommand.cancel();
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }
}
