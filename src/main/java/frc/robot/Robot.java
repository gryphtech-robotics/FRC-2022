
/**
 * Copyright 2022 Gryphtech Robotics
 * #axelsgreat
 */

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// our stuff
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Systems.Auto;
import frc.robot.Systems.Drive;
import frc.robot.Systems.Intake;
import frc.robot.Systems.Limelight;
import frc.robot.Systems.Launcher.Flywheel;
import frc.robot.Systems.Launcher.BallStopper;
import frc.robot.Systems.Launcher.Angle;
import frc.robot.Systems.Launcher.LaunchMath;
import frc.robot.Systems.Climber;

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

        tx = (float) Limelight.tx.getDouble(0.0);

        Flywheel.init(23);        //23 is the flywheel angle
        Intake.init();
        Limelight.init(104);        //104 is the target height
        Drive.init(driverController);
        Angle.init();
        Climber.init();

        SmartDashboard.setDefaultNumber("Launch Angle", 75);
        SmartDashboard.setDefaultNumber("Velocity Coefficient", 1.1);
    }

    @Override
    public void autonomousInit(){
        Auto.init();
    }

    @Override
    public void autonomousPeriodic() {
         Auto.periodic();
    }

    @Override
    public void teleopPeriodic() {
        tx = (float) Limelight.tx.getDouble(0.0);
        double velocityCoeffecient = LaunchMath.velocityCoeffecient(Limelight.distanceFromLimelightToGoalInches);

        Angle.checkLimitSwitch();
        Limelight.periodic();
        Drive.periodic();
        Angle.periodic();

        // Intake
        if (driverController.getRawButton(1)) {
            Intake.intake.set(0.75);
        } else if( driverController.getRawButton(1) && driverController.getRawButton(2)){
            Intake.intake.set(-0.75);
        }else{Intake.intake.stopMotor();}

        //Flywheel
        if(Limelight.distanceFromLimelightToGoalInches <= 145){
            velocityCoeffecient += 0.08;
        }
        if(systemsController.getRawButton(5)){
            Flywheel.rpm(velocityCoeffecient * LaunchMath.mpsToRpm(LaunchMath.getVelocity(LaunchMath.inTom(Limelight.distance()), Math.toRadians(Angle.getAngle()))));
        } else {
            Flywheel.flyWheel.stopMotor();
        }

        // pneumatics
        if (systemsController.getRawButton(6)) {
            BallStopper.launch();
        }
        
        // Limelight Lock-on
        if (driverController.getRawButton(4) || driverController.getRawButton(11)) {
            Drive.goLoR(tx + tx/2);
        } else if (systemsController.getRawButton(4) && Limelight.limeX == 0.0) {
            Drive.stop();
        }

        //Angle
        if (systemsController.getRawButton(7)){
            Angle.zeroLimitSwitch();
        }

        if (((Angle.checkPosition() > 0) || (systemsController.getRawAxis(0) > 0)) && ((Angle.checkPosition() < 50) || (systemsController.getRawAxis(0) < 0))){
            Angle.setSpeed(systemsController.getRawAxis(0)*0.05);
        }else{
            Angle.setSpeed(0.0);
        }

        if (systemsController.getRawButton(1)){
            Angle.manualAngle = false;
            Angle.setRotations(LaunchMath.angleToRotations(LaunchMath.Angle(Limelight.distance())));
        }else{
            Angle.manualAngle = false;
        }
    
        //climber
        if (driverController.getRawButton(5)) {
            Climber.elevator.set(-0.5);
        }
        if (driverController.getRawButton(3)) {
            Climber.elevator.set(0.5);
        }else if(!driverController.getRawButton(3) && !driverController.getRawButton(5)){
            Climber.elevator.set(0.0);
        }
        if(Climber.ls.get()){
            Climber.elevator.set(0.0);
        }
    }
}
