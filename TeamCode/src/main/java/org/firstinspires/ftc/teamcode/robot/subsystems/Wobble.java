package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

public class Wobble implements Subsystem {
    // Hardware map
    private HardwareMap hardwareMap;

    private DcMotor arm;
    private Servo grip;

    public enum ARM_TARGETS {
        UP,
        DOWN,
        OUT;

        public int getTarget(){
            switch (this){
                case UP:    return 1000;
                case OUT:   return 0;
                case DOWN:  return -1000;
                default:    return 0;
            }
        }
    }
    public enum GRIP_TARGETS {
        OPEN,
        CLOSE;

        public double getTarget(){
            switch (this){
                case OPEN:  return 1;
                case CLOSE: return 0;
                default:    return 0;
            }
        }
    }

    private double power;

    private int targetPos;
    private int targetOffset = 0;

    public Wobble(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;


    }

    @Override
    public void initHardware() {
        arm = hardwareMap.get(DcMotor.class, "arm");
        grip = hardwareMap.get(Servo.class, "grip");

        targetPos = arm.getCurrentPosition();

        arm.setTargetPosition(targetPos);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        grip.setPosition(GRIP_TARGETS.OPEN.getTarget());
    }

    @Override
    public void periodic() {
        arm.setTargetPosition(targetPos);
    }

    public void resetArmTargets(){
        targetOffset = arm.getCurrentPosition() - targetOffset;
    }



    public void setArmTargetPos(int targetPos){
        this.targetPos = targetPos - targetOffset;
    }

    public void setGripTargetPos(double pos){
        grip.setPosition(pos - targetOffset);
    }

    public int getArmPos(){
        return arm.getCurrentPosition() - targetOffset;
    }

    public int getArmTargetPos(){
        return arm.getTargetPosition() - targetOffset;
    }

    public double getGripPos(){
        return grip.getPosition();
    }

    public void setArmRunMode(DcMotor.RunMode runMode){
        arm.setMode(runMode);
    }

    public DcMotor.RunMode getArmRunMode(){
        return arm.getMode();
    }

    public void setArmPower(double power){
        this.power = power;
    }

    public double getArmPower(){
        return power;
    }




}
