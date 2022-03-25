package frc.robot.Systems.Launcher;

import frc.robot.Systems.Limelight;
import frc.robot.Utility.Gains;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;

public class Flywheel {
    public static TalonFX flyWheel;

	public static final int kSlotIdx = 0;
	public static final int kPIDLoopIdx = 0;
	public static final int kTimeoutMs = 30;
	public final static Gains kGains_Velocit = new Gains( 0.1, 0.001, 5, 1023.0/20660.0,  300,  1.00);

    public static void init() {

		  // rpm thing
		flyWheel = new TalonFX(7);

        flyWheel.configFactoryDefault();
		
		/* Config neutral deadband to be the smallest possible */
		flyWheel.configNeutralDeadband(0.001);

		/* Config sensor used for Primary PID [Velocity] */
        flyWheel.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, kPIDLoopIdx, kTimeoutMs);
											
		/* Config the peak and nominal outputs */
		flyWheel.configNominalOutputForward(0, kTimeoutMs);
		flyWheel.configNominalOutputReverse(0, kTimeoutMs);
		flyWheel.configPeakOutputForward(1, kTimeoutMs);
		flyWheel.configPeakOutputReverse(-1, kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */
		flyWheel.config_kF(kPIDLoopIdx, kGains_Velocit.kF, kTimeoutMs);
		flyWheel.config_kP(kPIDLoopIdx, kGains_Velocit.kP, kTimeoutMs);
		flyWheel.config_kI(kPIDLoopIdx, kGains_Velocit.kI, kTimeoutMs);
		flyWheel.config_kD(kPIDLoopIdx, kGains_Velocit.kD, kTimeoutMs);
    }

	public static void velocity(double velocity){
		System.out.println(" got here "  + velocity);
        flyWheel.set(ControlMode.Velocity, Limelight.mpsToRpm(velocity) / 60 / 100);
    }
}
