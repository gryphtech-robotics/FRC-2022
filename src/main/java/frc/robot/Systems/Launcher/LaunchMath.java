package frc.robot.Systems.Launcher;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LaunchMath {

    public static double angle;

    public static double getVelocity(double distance, double angle){
        return Math.sqrt((9.81*(Math.pow(distance, 2)))/(Math.sin(2*angle)*distance - Math.pow(Math.cos(angle), 2)*2*2.64));
    }
    public static double inTom(double inches){
        return inches * 0.0254;
    }
    public static double angleToRotations(double angle){
        double atr = (71.2278 + (-370.823*2.71828)/((90 - angle) + 4.20469));
        return atr;
    }
    public static double rotationsToAngle(double rotations){
        return 90 - (-370.823*(2.71828/(rotations - 71.2278))-4.20469);
    }
    public static double mpsToRpm(double velocity){
        return 1 * velocity * 60 * 2 / (6.2*3.1415926535*0.0254);
        
        // change rpm^
    }
    public static double Angle(double distance){
        double Angle = (6.7 * 500 / (0.5 * (distance + 136)) + 45.6);
        SmartDashboard.putNumber("Angle", Angle);
        return Angle;
    }

    public static double velocityCoeffecient(double distance) {
        // change 0.08 if shit's no worky
        return (0.17/58) * distance + 0.667586206897 + 0.06;
    }
}