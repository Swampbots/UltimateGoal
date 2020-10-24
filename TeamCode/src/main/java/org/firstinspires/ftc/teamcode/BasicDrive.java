package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Mecanum Driver Control", group = "TeleOp")
public class BasicDrive extends OpMode {



    BasicDriveHardware hardware = new BasicDriveHardware();




    public void init() {
        hardware.init(hardwareMap);

        hardware.pulleyLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.pulleyRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        hardware.pulleyLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hardware.pulleyRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);






        telemetry.addLine("Ready");
        telemetry.update();
    }

    public void loop() {


        // Do the math
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double twist = -gamepad1.right_stick_x;





        hardware.pulleyLeft.setPower(gamepad2.left_stick_y);
        hardware.pulleyRight.setPower(gamepad2.right_stick_y);



        // Set the power
        hardware.setMecanumPower(drive, strafe, twist, .6);




        // Tape drive controls


        telemetry.addLine("Running");
        telemetry.addLine();
        telemetry.addData("Wheel driver speed mod", .6);
        telemetry.addLine();
    }


}
