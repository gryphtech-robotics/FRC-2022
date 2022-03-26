package frc.robot.Systems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    public static NetworkTable table;

    public static void init() {
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
        SmartDashboard.putNumber("LimelightX: ", limeX);
        SmartDashboard.putNumber("LimelightY: ", limeY);
        SmartDashboard.putNumber("LimelightArea: ", limeArea);
        SmartDashboard.putBoolean("Target acquired? ", limeTarget);
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
        double targetHeight = 104;
        // distance from ground to upper hub rim rim is 104 inches

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

        //calculate distance
        distanceFromLimelightToGoalInches = (targetHeight - cameraHeight)/Math.tan(angleToGoalRadians);
        //only get distance when it can see the target
        
        return distanceFromLimelightToGoalInches;
    
    }

    public static double rpmToMps(double rpm) {
        // 6.2 is the wheel diameter for future reference
        return rpm * 6.2 * 3.1415 / 39.3701 / 60  ;
    }

    public static double mpsToRpm(double mps) {
        return 60 / 2 * (3.1415 * 6.2) * mps;
    }

	/**
	 * This method returns the required intial velocity to get the ball into the net from the given distance.
	 * It does not garuntee an entry angle, and it does assumes you have the firing angle.
	 * @param distance The distance between you and the edge of the net. 
	 * @param angle The firing angle
	 * @return The initial velocity in m/s.
	 */
	public static double variableEntryVelocity(double distance, double angle) {
		double meters = distance * 0.0254;

		double numerator = 9.81*Math.pow(meters, 2);
		double denominator = Math.sin(2*angle)*meters - Math.pow(Math.cos(angle),2)*meters;

		return Math.sqrt(numerator/denominator);
	}

	/**
	 * This method returns the initial velocity required to shoot the ball into the net, *and have it enter the net at a 45 degree angle*. 
	 * Crucially, this method assumes that for distances under 3 meters, the robot will fire at a 60 degree angle, and for shots over 10 meters away, an angle of 30 degrees.
	 * This does not set the angle-adjuster to the required angles, just assumes that you already have. 
	 * @param distance
	 * @return The initial velocity (in m/s) required to hit the target at a 45 degree entry angle. 
	 */
	public static double setEntryVelocity(double distance) { 
		double meters = distance * 0.0254;
		double angle = 34;

		double numerator = 32.18504 * Math.pow(meters, 2);
        // change 110 back to 98 if problem
		double denominator = 2 * (130 - Math.tan(60) * meters) * Math.pow(Math.cos(angle), 2);

		return Math.sqrt(numerator/denominator);
	}
}
