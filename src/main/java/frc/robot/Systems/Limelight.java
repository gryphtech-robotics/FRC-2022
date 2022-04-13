package frc.robot.Systems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.Systems.Launcher.LaunchMath;

public class Limelight {
    public static NetworkTableEntry tx;
    public static NetworkTableEntry ty;
    public static NetworkTableEntry ta;
    public static NetworkTableEntry tv;

    public static double limeX;
    public static double limeY;
    public static double limeArea;
    public static boolean limeTarget;
    public static double dist;
    public static double distanceFromLimelightToGoalInches; 

    public static double targetHeight;

    public static NetworkTable table;

    /**
     * Initializes the limelight subsystem(s).
     * @param th Target Height in inches.
     */
    public static void init(double th) {
        targetHeight = th;

        table = NetworkTableInstance.getDefault().getTable("limelight");
        
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");

        table.getEntry("ledMode").setNumber(3.0);
    }

    public static void periodic () {
        //read values periodically
        limeX = tx.getDouble(0.0);
        limeY = ty.getDouble(0.0);
        limeArea = ta.getDouble(0.0);
        double tempLT = tv.getDouble(0.0);


        limeTarget = (tempLT == 0.0 ? false : true);
        
        distance();

        //post to smart dashboard periodically
        //SmartDashboard.putNumber("LimelightX: ", limeX);
        //SmartDashboard.putNumber("LimelightY: ", limeY);
        //SmartDashboard.putNumber("LimelightArea: ", limeArea);
        SmartDashboard.putBoolean("Target Acquired? ", limeTarget);

        if (limeTarget == true) {
            SmartDashboard.putNumber("Dist.", dist);
            dist = distanceFromLimelightToGoalInches;
        }
    }

    public static double distance() { 
        double targetOffsetAngle_Vertical = ty.getDouble(0.0);

        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 30;

        // distance from the center of the Limelight lens to the floor
        double cameraHeight = 25; 

        // distance from the target to the floor
        // distance from ground to upper hub rim rim is 104 inches

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

        //calculate distance
        distanceFromLimelightToGoalInches = (targetHeight - cameraHeight)/Math.tan(angleToGoalRadians) +24;
        //only get distance when it can see the target
        
        return (targetHeight - cameraHeight)/Math.tan(angleToGoalRadians) +24;
    }
}
    
