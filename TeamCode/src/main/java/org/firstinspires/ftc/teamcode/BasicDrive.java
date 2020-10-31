package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Mecanum Driver Control", group = "TeleOp")
public class BasicDrive extends OpMode {



    BasicDriveHardware hardware = new BasicDriveHardware();




    public void init() {
        hardware.init(hardwareMap);








        telemetry.addLine("Ready");
        telemetry.update();
    }

    public void loop() {


        // Do the math
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double twist = -gamepad1.right_stick_x;








        // Set the power
        hardware.setMecanumPower(drive, strafe, twist, .6);




        // Tape drive controls


        telemetry.addLine("Running");
        telemetry.addLine();
        telemetry.addData("Wheel driver speed mod", .6);
        telemetry.addLine();
    }


}
