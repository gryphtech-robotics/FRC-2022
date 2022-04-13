package frc.robot.Systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake{
    
    public static CANSparkMax intake;

    public static double intakeDownTime;
    public static double intakeUpTime;

    public static void init(){
        intake = new CANSparkMax(26, MotorType.kBrushed);
        intake.restoreFactoryDefaults();
    }

}