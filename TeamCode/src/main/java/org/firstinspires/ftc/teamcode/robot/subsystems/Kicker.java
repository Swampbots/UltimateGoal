package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Kicker implements Subsystem {
    // Hardware Map
    private HardwareMap hardwareMap;

    private Servo kicker;

    private final double IN = 0.0;
    private final double OUT = 1.0;

    private double targetPos = IN;

    public Kicker(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }

    @Override
    public void initHardware() {
        kicker = hardwareMap.get(Servo.class, "kicker");
    }

    @Override
    public void periodic() {
        kicker.setPosition(targetPos);
    }

    public void setTargetPos(double targetPos){
        this.targetPos = targetPos;
    }

    public double getTargetPos() {
        return targetPos;
    }
}
