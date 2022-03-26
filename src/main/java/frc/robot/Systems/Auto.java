package frc.robot.Systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.Systems.Launcher.Angle;
import frc.robot.Systems.Launcher.BallStopper;
import frc.robot.Systems.Launcher.Flywheel;

public class Auto {
    

    public static void init(){
        
        //Drive       
        Joystick ignoreMe = new Joystick(0);
        Drive.init(ignoreMe);
        //Limelight
        Limelight.init();
        //Angle
        Angle.init();
        
    }

    public static void run(){
        Drive.lDrive0.set(-0.5);
        Drive.rDrive0.set(-0.5);
        Timer.delay(3);
        Drive.stop();
        if(!Limelight.limeTarget){
            Drive.goLoR((float)Limelight.tx.getDouble(0.0));
        }
        double dist = Limelight.distanceFromLimelightToGoalInches;
        double v = Limelight.setEntryVelocity(dist);
        Flywheel.velocity(v);
        Timer.delay(1);
        BallStopper.launch();

    }
}
