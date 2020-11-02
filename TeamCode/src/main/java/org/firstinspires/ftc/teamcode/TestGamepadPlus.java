package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestGamepadPlus extends OpMode {

    GamepadPlus gp1;

    @Override
    public void init() {
        gp1 = new GamepadPlus(gamepad1);
    }

    @Override
    public void loop() {
        gp1.update();

        String button = "circle";

        telemetry.addData("Is "+ button +" pressed?",gp1.isPressed(button));
        telemetry.addData("Time "+ button +" pressed",gp1.howLongPressed(button));
    }
}
