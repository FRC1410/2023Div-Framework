package org.frc1410.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import org.frc1410.framework.scheduler.Subsystem;
import org.frc1410.robot.Constants;

public class Intake implements Subsystem {

    private final CANSparkMax motor = new CANSparkMax(Constants.INTAKE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

    public Intake() {
        motor.restoreFactoryDefaults();
        motor.setInverted(true);
    }

    public double getSpeed() {
        return motor.get();
    }

    public void setSpeed(double speed) {
        motor.set(speed);
    }
}