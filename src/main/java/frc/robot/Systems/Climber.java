
package frc.robot.Systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
/*
/**
 * This class controls our robot's elevator.
 * @author Axel Greavette & Sierra Thomson
 */
public class Climber {
    public static CANSparkMax elevator;
    public static DigitalInput ls; 

    public static void init() {
        elevator = new CANSparkMax(28, MotorType.kBrushed);
        elevator.restoreFactoryDefaults();

        
        ls = new DigitalInput(1);
    }

    //Motor gear ratio = 50:1


    }
