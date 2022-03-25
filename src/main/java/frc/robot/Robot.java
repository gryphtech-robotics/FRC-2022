
/**
 * Copyright 2020 Gryphtech Robotics
 * # axelsgreat
 */

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

// our stuff
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Systems.Auto;
import frc.robot.Systems.Drive;
import frc.robot.Systems.Intake;
import frc.robot.Systems.Limelight;
import frc.robot.Systems.Launcher.Flywheel;
import frc.robot.Systems.Launcher.BallStopper;

public class Robot extends TimedRobot {

    // Initialize Joysticks
    public Joystick driverController;

    float tx;

    @Override
    public void robotInit() {
        // Joysticks
        driverController = new Joystick(0);
        // Flywheel
        Flywheel.init();
        // Intake
        Intake.init();
        // Limelight
        Limelight.init();
        // Drive
        Drive.init(driverController);

        tx = (float) Limelight.tx.getDouble(0.0);
    }

    @Override
    public void teleopPeriodic() {
        tx = (float) Limelight.tx.getDouble(0.0);
        SmartDashboard.putNumber("tx", tx);

        // Limelight Periodic Updates
        Limelight.periodic();
        // Drive
        Drive.drive();

        // Intake
        if (driverController.getRawButton(1)) {
            Intake.set(0.5);
        } else if (driverController.getRawButton(1) && driverController.getRawButton(2)) {
            Intake.set(-0.5);
        } else {
            Intake.set(0.0);
        }

        /*if (driverController.getRawButton(3)) {
            // doesnt actually set reference right now just logs shit
            Angle.setAngle(driverController.getRawAxis(3));
        }*/

        if (Limelight.limeTarget && driverController.getRawButton(5)) {
            double dist = Limelight.distanceFromLimelightToGoalInches;
            double v = Limelight.setEntryVelocity(dist);

            Flywheel.velocity(v);
            //Flywheel.flyWheel.set(ControlMode.PercentOutput, .25);
            SmartDashboard.putNumber("Velocity", v);
        } else {
            Flywheel.flyWheel.set(ControlMode.PercentOutput, 0);
        }

        // penumatics
        if (driverController.getRawButton(6)) {
            BallStopper.launch();
        }
        // Limelight Lock-on
        if (driverController.getRawButton(4)) {
            Drive.goLoR(tx);
        } else if (driverController.getRawButton(4) && Limelight.limeX == 0.0) {
            Drive.stop();
        }
    }

    @Override
    public void autonomousInit() {
        Auto.init();
    }

    @Override
    public void autonomousPeriodic() {
        Auto.run();
    }
}
