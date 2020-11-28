package org.firstinspires.ftc.teamcode.robot.commands.teleop;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.robot.subsystems.Wobble;

public class TeleOpWobbleControl implements Command {
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private Wobble wobble;

    public TeleOpWobbleControl(Wobble wobble, Gamepad gamepad1, Gamepad gamepad2){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.wobble = wobble;
    }

    @Override
    public void start() {
        wobble.setArmPower(0);
    }

    @Override
    public void periodic() {
        if(gamepad1.dpad_up){
            wobble.setArmTargetPos(Wobble.ARM_TARGETS.UP.getTarget());
        }
        if(gamepad1.dpad_down){
            wobble.setArmTargetPos(Wobble.ARM_TARGETS.DOWN.getTarget());
        }
        if(gamepad1.dpad_left || gamepad1.dpad_right){
            wobble.setArmTargetPos(Wobble.ARM_TARGETS.OUT.getTarget());
        }

        if(gamepad1.a){
            wobble.setArmPower(.2);
        } else {
            wobble.setArmPower(0);
        }

        if(gamepad1.x){
            wobble.setGripTargetPos(Wobble.GRIP_TARGETS.CLOSE.getTarget());
        } else {
            wobble.setGripTargetPos(Wobble.GRIP_TARGETS.OPEN.getTarget());
        }
    }

    @Override
    public void stop() {
        wobble.setArmPower(0);
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}
