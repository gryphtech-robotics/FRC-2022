
/**
 * Copyright 2020 Gryphtech Robotics
 * # axelsgreat
 */

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

//Driver control
import edu.wpi.first.wpilibj.Joystick;

//drive!
import frc.robot.Systems.Drivetrain;

public class Robot extends TimedRobot {

  // Initialize Joysticks
  public Joystick driverController;
  public Joystick systemsController;

  public static boolean go = false;

  @Override
  public void robotInit() {
    System.out.println("hahahahahha");

    // Joysticks
    driverController = new Joystick(0);
    
    //motor control
    Drivetrain.init(driverController);

  }

  @Override
  public void robotPeriodic() {
    
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopPeriodic() {
    
    Drivetrain.drive();

  } 

  @Override
  public void testPeriodic() {
  
  }
}
