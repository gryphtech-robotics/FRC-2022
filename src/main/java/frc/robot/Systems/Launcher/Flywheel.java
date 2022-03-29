package frc.robot.Systems.Launcher;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Systems.Limelight;

public class Flywheel {
    public static CANSparkMax flyWheel;

    public static RelativeEncoder encoder;
    public static SparkMaxPIDController PID;

    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
    public static double sP;
    
    /**
     * This function initializes the motors used to drive the robot.
     * To do this it assigns the CANSparkMax motors to the public variables leftThruster, rightThruster, leftSideWheel, rightSideWheel, and angleSetter.
     */
    public static void init () {
        flyWheel = new CANSparkMax(7, MotorType.kBrushless);

        flyWheel.restoreFactoryDefaults();

        PID = flyWheel.getPIDController();
        encoder = flyWheel.getEncoder();

        flyWheel.setOpenLoopRampRate(1);
        flyWheel.setClosedLoopRampRate(1);

        PID.setFeedbackDevice(encoder);
        PID.setFF(0.0002);
        PID.setP(0);
        
        sP = 0;

    }

    /**
     * This function will simply work.
     */
    public static void velocity (double distance) {      
		  PID.setReference(Limelight.mpsToRpm(Limelight.variableEntryVelocity(distance, 22)), ControlType.kVelocity);
    }
}