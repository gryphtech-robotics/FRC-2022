package frc.robot.Systems.Launcher;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Angle {
    
    public static CANSparkMax angleMaker;

    public static int startAngle = 45;
    public static double time;
    public static double setTime;
    public static double speed = 0.25;
    public static int anglePercent;
    private static final SparkMaxAlternateEncoder.Type kAltEncType = SparkMaxAlternateEncoder.Type.kQuadrature;
    private static final int kCPR = 8192;
    private static RelativeEncoder angle_alternateEncoder;
    public static double thrusterSpeed;
    public static double overShoot;
    public static double underShoot;
    public static DigitalInput limitSwitch;
   

    private static SparkMaxPIDController angle_pidController;
    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public static void init(){
        angleMaker = new CANSparkMax(5, MotorType.kBrushless);
        //angleMaker.restoreFactoryDefaults();
        angle_alternateEncoder = angleMaker.getAlternateEncoder(kAltEncType, kCPR);
        angle_pidController = angleMaker.getPIDController();
        limitSwitch = new DigitalInput(0);

        zeroLimitSwitch();
        
        //PID for the angle of the launcher below 
        // PID coefficients
        
        kP = 0.1; 
        kI = 1e-4;
        kD = 1; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

        // set PID coefficients
        angle_pidController.setP(kP);
        angle_pidController.setI(kI);
        angle_pidController.setD(kD);
        angle_pidController.setIZone(kIz);
        angle_pidController.setFF(kFF);
        angle_pidController.setOutputRange(kMinOutput, kMaxOutput);

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
        SmartDashboard.putNumber("Set Rotations", 0);
    }
    
    public static void periodic(){
    
        //PID for the angle below
        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != kP)) { angle_pidController.setP(p); kP = p; }
        if((i != kI)) { angle_pidController.setI(i); kI = i; }
        if((d != kD)) { angle_pidController.setD(d); kD = d; }
        if((iz != kIz)) { angle_pidController.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { angle_pidController.setFF(ff); kFF = ff; }

        if((max != kMaxOutput) || (min != kMinOutput)) { 
            angle_pidController.setOutputRange(min, max); 
            kMinOutput = min; kMaxOutput = max;
        }
    }


    public static void setAngle(Double rotations) {
    
        //angle_pidController.setReference(rotations, CANSparkMax.ControlType.kPosition);

        SmartDashboard.putNumber("SetPoint", rotations);
        SmartDashboard.putNumber("ProcessVariable", angle_alternateEncoder.getPosition());

    }
    
    public static void zeroLimitSwitch(){
        System.out.println("we get here");
        angleMaker.set(-.5);
        if (limitSwitch.get()){
            angleMaker.set(0.0);
            angleMaker.restoreFactoryDefaults();
        }
    }
}

