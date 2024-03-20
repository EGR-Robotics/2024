package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

// Auton
import edu.wpi.first.wpilibj.Timer; // TODO: delete this when switched to pathplanner

// Logging
import frc.robot.lib.util.loggingUtil.LogManager;

// Commands
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    private Command autonomousCommand;

    private RobotContainer robotContainer;

    private Timer timer = new Timer(); // Timer for auton

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        LogManager.log();
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        timer.reset();
        timer.start();

        autonomousCommand = robotContainer.getAutonomousCommand();
        autonomousCommand.schedule();
    }

    @Override
    public void autonomousPeriodic() {
        // if (autonomousCommand != null)
            // autonomousCommand.schedule();
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null)
            autonomousCommand.cancel();
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}
}
