
/**
 * Copyright 2020 Gryphtech Robotics
 * # axelsgreat
 */

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// our stuff
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Systems.Drive;
import frc.robot.Systems.Intake;
import frc.robot.Systems.Limelight;
import frc.robot.Systems.Launcher.Flywheel;
import frc.robot.Systems.Launcher.BallStopper;
import frc.robot.Systems.Launcher.Angle;

public class Robot extends TimedRobot {

    // Initialize Joysticks
    public Joystick driverController;
    public Joystick systemsController; 

    public boolean toggle = false;

    float tx;

    @Override
    public void robotInit() {
        driverController = new Joystick(0);
        systemsController = new Joystick(1);

        Flywheel.init();
  //      Intake.init();
        Limelight.init();
        Drive.init(driverController);
        tx = (float) Limelight.tx.getDouble(0.0);
    }

    @Override
    public void teleopPeriodic() {
        tx = (float) Limelight.tx.getDouble(0.0);
        SmartDashboard.putNumber("tx", tx);

        Limelight.periodic();

        Drive.periodic();

        // Intake
      /*  if (systemsController.getRawButton(1)) {
            Intake.set(0.66);
        } else { Intake.set(0.0); }
*/
        if(systemsController.getRawButton(5)) {
            Flywheel.velocity(Limelight.distance());
        } else { Flywheel.flyWheel.stopMotor(); }


        // pneumatics
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
}
