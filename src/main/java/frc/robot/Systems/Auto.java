package frc.robot.Systems;

import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Systems.Launcher.Angle;
import frc.robot.Systems.Launcher.BallStopper;
import frc.robot.Systems.Launcher.Flywheel;
import frc.robot.Systems.Launcher.LaunchMath;

public class Auto {
    

    public static void init(){
        Joystick ignoreMe = new Joystick(0); //this is because i wanted to reuse methods and although a joystick is normally required this one is simply ignored because i take direct control of motor outputs
        
        Drive.init(ignoreMe);
        //Limelight
        Limelight.init(104);
        //Angle
        Angle.init();
    }

    public static void periodic(){
        double velocityCoeffecient = LaunchMath.velocityCoeffecient(Limelight.distanceFromLimelightToGoalInches);

        Angle.zeroLimitSwitch();
        if(Limelight.distanceFromLimelightToGoalInches != 150) {    
            Drive.lDrive0.set(-0.5);
            Drive.rDrive0.set(0.5); 
        } else {
            Angle.manualAngle = false;

            Drive.stop();
            Angle.setRotations(LaunchMath.angleToRotations(LaunchMath.Angle(Limelight.distance())));
            Flywheel.rpm(velocityCoeffecient * LaunchMath.mpsToRpm(LaunchMath.getVelocity(LaunchMath.inTom(Limelight.distance()), Math.toRadians(Angle.getAngle()))));
            BallStopper.autoLaunch();
        }
    }
}
