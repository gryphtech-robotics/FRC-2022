package frc.robot.Systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake{
    
    public static CANSparkMax intake;

    public static double intakeDownTime;
    public static double intakeUpTime;

    public static void init(){
        intake = new CANSparkMax(6, MotorType.kBrushed);
        intake.restoreFactoryDefaults();
    }
    
    public static void set(double in){
        // in should be 0.5 or -0.5
        if(in == 0.0) {
            intake.stopMotor();
        } else {
            intake.set(in);
        }
    }
}