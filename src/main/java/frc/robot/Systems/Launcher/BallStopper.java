package frc.robot.Systems.Launcher;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class BallStopper{
    public static DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);

    public static void launch(){
        BallStopper.solenoid.set(Value.kReverse);
        Timer.delay(0.5);
        BallStopper.solenoid.set(Value.kOff);
        Timer.delay(1);
        BallStopper.solenoid.set(Value.kForward);
        Timer.delay(0.5);
        BallStopper.solenoid.set(Value.kOff);
    }
}