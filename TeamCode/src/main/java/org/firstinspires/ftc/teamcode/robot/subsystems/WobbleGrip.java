package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WobbleGrip implements Subsystem {
    // Hardware map
    private HardwareMap hardwareMap;

    private Servo grip;

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
    public WobbleGrip(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }
}
