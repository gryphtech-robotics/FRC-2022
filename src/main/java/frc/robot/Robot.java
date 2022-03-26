
/**
 * Copyright 2020 Gryphtech Robotics
 * # axelsgreat
 */

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

// our stuff
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Systems.Auto;
import frc.robot.Systems.Drive;
import frc.robot.Systems.Intake;
import frc.robot.Systems.Limelight;
import frc.robot.Systems.Launcher.Flywheel;
import frc.robot.Systems.Launcher.BallStopper;
//import frc.robot.Systems.Launcher.Angle;

public class Robot extends TimedRobot {

    // Initialize Joysticks
    public Joystick driverController;
    public Joystick systemsController; 

    public TalonFX t;
    public boolean toggle = false;

    float tx;

    @Override
    public void robotInit() {
        // Joysticks
        driverController = new Joystick(0);
        systemsController = new Joystick(1);
        // Flywheel
        Flywheel.init();
        // Intake
        Intake.init();
        // Limelight
        Limelight.init();
        // Drive
        Drive.init(driverController);
        //angle
        //Angle.init();

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
        if (systemsController.getRawButton(1)) {
            Intake.set(0.66);
        } else {
            Intake.set(0.0);
        }

        if(systemsController.getRawButton(5)) {
            Flywheel.flyWheel.set(ControlMode.PercentOutput, systemsController.getRawAxis(3));

        } else {
            Flywheel.flyWheel.set(ControlMode.PercentOutput, 0);
        }

        // penumatics
        if (systemsController.getRawButton(6)) {
            BallStopper.launch();
        }
        // Limelight Lock-on
        if (systemsController.getRawButton(4)) {
            Drive.goLoR(tx);
        } else if (systemsController.getRawButton(4) && Limelight.limeX == 0.0) {
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

    @Override
    public void testInit() {
        Limelight.init();

        driverController = new Joystick(0);

        t = new TalonFX(7);
  
        t.configFactoryDefault();
    }

    @Override
    public void testPeriodic() {
        double p = ((-driverController.getRawAxis(3))+1)/2;

        t.set(ControlMode.PercentOutput, p);
    
        SmartDashboard.putNumber("Motor", p);
        SmartDashboard.putNumber("Dist", Limelight.distance());
    
        if (driverController.getRawButton(6)) {
            BallStopper.launch();
        }
    }
}
