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


        //Should return 'true' while the button is pressed
        telemetry.addData("Is "+ button +" pressed?",gp1.isPressed(button));
        telemetry.addLine();

        //Should return the amount of milliseconds the button was pressed"
        telemetry.addData("Time "+ button +" pressed",gp1.howLongPressed(button));
        telemetry.addLine();

        //Should return 'true' for the first few milliseconds after the button was pressed
        telemetry.addData("Was "+button+" just pressed?",gp1.wasJustPressed(button));
    }
}
