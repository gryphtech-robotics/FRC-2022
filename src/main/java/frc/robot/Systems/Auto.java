package frc.robot.Systems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.Systems.Launcher.Angle;
import frc.robot.Systems.Launcher.BallStopper;
import frc.robot.Systems.Launcher.Flywheel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Systems.Launcher.LaunchMath;

public class Auto {
    

    public static void init(){
        
        //Drive       
        Joystick ignoreMe = new Joystick(0);
        Drive.init(ignoreMe);
        //Limelight
        Limelight.init(104);
        //Angle
        Angle.init();
        
    }

    public static void run(){
        Angle.zeroLimitSwitch();
        Drive.lDrive0.set(-0.5);
        Drive.rDrive0.set(0.5);
        Timer.delay(0.5);
        Drive.stop();

        Flywheel.rpm(1.5 * (4000 + (SmartDashboard.getNumber("anotherCoefficient", 1) * (-4000 + SmartDashboard.getNumber("Velocity Coefficient", 1.3) * LaunchMath.mpsToRpm(LaunchMath.getVelocity(LaunchMath.inTom(Limelight.distance()), Math.toRadians(Angle.getAngle())))))));

        Timer.delay(5);

        BallStopper.launch();

    }
}
