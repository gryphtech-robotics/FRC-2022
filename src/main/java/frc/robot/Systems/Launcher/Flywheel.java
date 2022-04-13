package frc.robot.Systems.Launcher;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Systems.Limelight;

public class Flywheel {
    public static CANSparkMax flyWheel;

    public static RelativeEncoder encoder;
    public static SparkMaxPIDController PID;

    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
    public static double sP;
    public static double angle;

    public static void init (double langle) {
        flyWheel = new CANSparkMax(27, MotorType.kBrushless);

        flyWheel.restoreFactoryDefaults();

        PID = flyWheel.getPIDController();
        encoder = flyWheel.getEncoder();

        flyWheel.setOpenLoopRampRate(1);
        flyWheel.setClosedLoopRampRate(1);

        PID.setFeedbackDevice(encoder);
        PID.setFF(0.0002);
        PID.setP(0.0005);
        
        sP = 0;

        angle = langle;

    }
    public static void rpm (double rpm){
        PID.setReference(rpm, ControlType.kVelocity);
        //System.out.println(encoder.getVelocity());
    }

}